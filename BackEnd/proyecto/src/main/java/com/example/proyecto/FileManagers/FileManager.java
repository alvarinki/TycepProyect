package com.example.proyecto.FileManagers;

import com.example.proyecto.dtos.AdminsUserData;
import com.example.proyecto.model.*;
import com.example.proyecto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.io.*;
import java.text.Normalizer;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class FileManager {

    @Autowired
    AsignaturaService asignaturaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    CursoService cursoService;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    HorarioService horarioService;

    @Autowired
    TutorService tutorService;

    public String mapProfesores(String ruta) {
        List<Profesor> profesores = new ArrayList<>();
        List<AdminsUserData> adminsUserData = new ArrayList<>();
        StringBuilder insercionCorrecta = new StringBuilder("Profesores registrados correctamente");
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    Profesor profesor = new Profesor();

                    if (comprobarNombre_Apellidos(datos[0].trim()) || comprobarNombre_Apellidos(datos[1].trim())) {
                        profesor.setNombre(datos[0].trim());
                        profesor.setApellidos(datos[1].trim());
                    } else return "Error en el formato del nombre o apellidos en la línea " + numeroLinea;

                    if (comprobarDNI(datos[2].trim())) profesor.setDni(datos[2].trim());
                    else return "Error en el formato del Dni en la línea" + numeroLinea;

                    if (esCorreoValido(datos[3].trim())) profesor.setCorreo(datos[3].trim());
                    else return "Formateo del correo en la línea " + numeroLinea + " incorrecto";

                    profesor.setContrasena(generarContrasena());
                    profesor.setDtype('P');
                    try {
                        Arrays.stream(datos[4].split(",")).toList().forEach(asig -> profesor.getAsignaturas().add(asignaturaService.findByNombre(asig)));
                    } catch (Exception e) {
                        return "Alguna asignatura no existe o hay un formateo incorrecto de estas en la línea " + numeroLinea;
                    }
                    String prefijo = getPrefijo(profesor.getNombre(), profesor.getApellidos());
                    String nombreUsuario;
                    do {
                        nombreUsuario = generarUsername(prefijo);
                    } while (usuarioService.findUsuarioByUsuario(nombreUsuario).isPresent());
                    profesor.setUsuario(nombreUsuario);

                    if (profesorService.findProfesorByDni(profesor.getDni()) == null) {
                        adminsUserData.add(new AdminsUserData(nombreUsuario, profesor.getContrasena()));
                        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                        profesor.setContrasena(passwordEncoder.encode(profesor.getContrasena()));
                        profesores.add(profesor);
                    } else insercionCorrecta.append(", se han intentado introducir profesores duplicados por dni");

                }
                numeroLinea++;
            }
        } catch (FileNotFoundException e) {
            return "No se ha encontrado el archivo";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profesorService.saveProfesores(profesores);
        notifyUsersData(ruta, adminsUserData, "Profesor");
        return insercionCorrecta.toString();
    }

    public String mapTutores(String ruta) {
        List<TutorLegal> tutores = new ArrayList<>();
        List<AdminsUserData> adminsUserData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    TutorLegal tutor = new TutorLegal();

                    if (comprobarNombre_Apellidos(datos[0].trim()) || comprobarNombre_Apellidos(datos[1].trim())) {
                        tutor.setNombre(datos[0].trim());
                        tutor.setApellidos(datos[1].trim());
                    } else return "Error en el formato del nombre o apellidos en la línea " + numeroLinea;

                    if (comprobarDNI(datos[2].trim())) tutor.setDni(datos[2].trim());
                    else return "Error en el formato del Dni en la línea" + numeroLinea;

                    try {
                        comprobarFormatoNumero(datos[3].trim());
                        tutor.setTelefContacto(datos[3].trim());
                    } catch (NumberFormatException e) {
                        return "Error en el formato del número de teléfono";
                    }
                    tutor.setDomicilio(datos[4].trim());
                    tutor.setDtype('T');
                    tutor.setContrasena(generarContrasena());
                    Set<Alumno> alumnos = new HashSet<>();
                    String[] dnis = datos[5].split(",");
                    for (String dni : dnis) {
                        if (comprobarDNI(dni)) {
                            Alumno a = alumnoService.findAlumnoByDni(dni);
                            if (a != null) alumnos.add(a);
                            else return "No se encuentra tutelado correspondiente a tutor en la línea " + numeroLinea;
                        } else
                            return "Error en el formato o existencia del dni de uno de los tutelados en la línea " + numeroLinea;
                    }

                    tutor.setAlumnos(alumnos);
                    String prefijo = getPrefijo(tutor.getNombre(), tutor.getApellidos());

                    String nombreUsuario;
                    do {
                        nombreUsuario = generarUsername(prefijo);
                    } while (usuarioService.findUsuarioByUsuario(nombreUsuario).isPresent());
                    tutor.setUsuario(nombreUsuario);
                    adminsUserData.add(new AdminsUserData(nombreUsuario, tutor.getContrasena()));
                    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                    tutor.setContrasena(passwordEncoder.encode(tutor.getContrasena()));
                    tutores.add(tutor);
                }
                numeroLinea++;
            }
        } catch (FileNotFoundException e) {
            return "El archivo no ha sido encontrado.";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tutorService.saveTutoresLegales(tutores);
        notifyUsersData(ruta, adminsUserData, "Tutor");
        return "Tutores registrados correctamente";
    }

    private static String getPrefijo(String nombre, String apellidos) {
        String prefijo;
        if (apellidos.split(" ").length > 1) {
            if (apellidos.split("-").length > 1)
                prefijo = nombre.substring(0, 1) + apellidos.charAt(0) + apellidos.split("-")[1].charAt(0);
            else prefijo = nombre.substring(0, 1) + apellidos.charAt(0) + apellidos.split(" ")[1].charAt(0);
        } else prefijo = nombre.substring(0, 1) + apellidos.charAt(0) + apellidos.charAt(1);
        return prefijo;
    }

    public String mapAdmins(String ruta) {
        List<Usuario> admins = new ArrayList<>();
        List<AdminsUserData> adminsUserData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                Usuario user = new Usuario();
                if (comprobarNombre_Apellidos(datos[0].trim()) || comprobarNombre_Apellidos(datos[1].trim())) {
                    if (usuarioService.findUsuarioByNombreAndApellidos(datos[0].trim(), datos[1].trim()) == null) {
                        user.setNombre(datos[0].trim());
                        user.setApellidos(datos[1].trim());
                    } else
                        return "El administrador que se ha intentado registrar en la linea " + numeroLinea + " ya existe";
                } else return "Error en el formato de nombre o apellidos en línea " + numeroLinea;

                user.setContrasena(generarContrasena());
                user.setDtype('A');
                String prefijo = getPrefijo(user.getNombre(), user.getApellidos());

                String nombreUsuario = "";
                do {
                    nombreUsuario = generarUsername(prefijo);
                } while (usuarioService.findUsuarioByUsuario(nombreUsuario).isPresent());
                user.setUsuario(nombreUsuario);

                adminsUserData.add(new AdminsUserData(nombreUsuario, user.getContrasena()));
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                user.setContrasena(passwordEncoder.encode(user.getContrasena()));
                admins.add(user);

                numeroLinea++;
            }
        } catch (FileNotFoundException e) {
            return "No se ha encontrado el archivo.";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        usuarioService.saveUsers(admins);
        notifyUsersData(ruta, adminsUserData, "Admin");
        return "Administradores registrados correctamente";
    }


    public String mapAlumnos(String ruta) {
        List<Alumno> alumnos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Alumno alumno = new Alumno();

                if (comprobarNombre_Apellidos(datos[0].trim()) && comprobarNombre_Apellidos(datos[1].trim())) {
                    alumno.setNombre(datos[0].trim());
                    alumno.setApellidos(datos[1].trim());
                } else return "Error en el formato de nombre o apellidos en la línea " + numeroLinea;

                int idCurso = cursoService.findCursoByNombre(datos[2].trim());
                if (idCurso != 0) alumno.setIdCurso(idCurso);
                else return "Hay un error en la introducción del curso en la línea " + numeroLinea;

                if (comprobarDNI(datos[3].trim())) alumno.setDni(datos[3].trim());
                else return "El dni introducido en la línea " + numeroLinea + " no existe";

                if (alumnoService.findAlumnoByDni(alumno.getDni()) == null) alumnos.add(alumno);
                numeroLinea++;
            }
            alumnoService.saveAlumnos(alumnos);
            return "Alumnos registrados correctamente";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String mapHorarios(String ruta) {
        List<Horario> horarios = new ArrayList<>();
        StringBuilder insercionCorrecta = new StringBuilder("Horarios registrados correctamente");
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Horario horario = new Horario();

                Asignatura asignatura = asignaturaService.findByNombre(datos[0].trim());
                if (asignatura != null) horario.setAsignatura(asignatura);
                else return "Error en la introducción de la asignatura en la línea " + numeroLinea;

                Dia dia = esDiaValido(datos[1].trim().toUpperCase());
                if (dia != null) horario.setDia(dia);
                else return "Error en la introducción del día en la línea " + numeroLinea;

                int hora = comprobarFormatoNumero(datos[2].trim());
                if (hora != 0) horario.setHora(hora);
                else return "Error en el formato de la hora en la línea " + numeroLinea;

                int idCurso = cursoService.findCursoByNombre(datos[3].trim());
                if (idCurso != 0) horario.setIdCurso(idCurso);
                else return "Hay un error en la introducción del curso en la línea " + numeroLinea;

                int aula = comprobarFormatoNumero(datos[4].trim());
                if (aula != 0) horario.setAula(aula);
                else return "Error en el formato del aula en la línea " + numeroLinea;


                Profesor profesor = profesorService.findProfesorByDni(datos[5].trim());
                if (profesor != null) horario.setIdProfesor(profesor.getId());
                else return "El dni introducido en la línea " + numeroLinea + " no corresponde a ningún profesor";

                Horario buscarHorario = horarioService.findHorarioByDiaAndHoraAndIdCurso(dia, hora, idCurso);
                if (buscarHorario != null) {
                    if (buscarHorario.getAsignatura() != asignatura || buscarHorario.getAula() != aula || buscarHorario.getIdProfesor() != profesor.getId()) {
                        horarioService.deleteHorario(buscarHorario);
                        insercionCorrecta.append(", se han repetido o modificado horarios");
                        horarios.add(horario);
                    }
                } else horarios.add(horario);

                numeroLinea++;
            }

            horarioService.saveHorarios(horarios);
            return insercionCorrecta.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String deleteByDniList(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            List<?> ids = new ArrayList<>();
            String tipo = br.readLine();
            switch (tipo) {
                case "Profesores" -> ids = new ArrayList<Profesor>();
                case "Tutores legales" -> ids = new ArrayList<TutorLegal>();
                case "Alumnos" -> ids = new ArrayList<Alumno>();
            }
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                switch (tipo) {
                    case "Profesores" -> {
                        Profesor p = profesorService.findProfesorByDni(linea);
                        if (p != null) ((List<Profesor>) ids).add(p);
                        else return "Error: No hay ningún profesor con el dni " + linea + " en la línea " + numeroLinea;
                        numeroLinea++;
                    }

                    case "Tutores legales" -> {
                        TutorLegal tutor = tutorService.findTutorLegalByDni(linea);
                        if (tutor != null) ((List<TutorLegal>) ids).add(tutor);
                        else return "Error: No hay ningún tutor con el dni " + linea + " en la línea " + numeroLinea;
                    }

                    case "Alumnos" -> {
                        Alumno alumno = alumnoService.findAlumnoByDni(linea);
                        if (alumno != null) ((List<Alumno>) ids).add(alumno);
                        else return "Error: No hay ningún alumno con el dni " + linea + " en la línea " + numeroLinea;
                    }

                    default -> {
                        return "Error: No se ha introducido el tipo de entidad a eliminar correcta";
                    }
                }
                numeroLinea++;
            }

            switch (tipo) {
                case "Profesores" -> {
                    profesorService.deleteProfesores(((List<Profesor>) ids));
                }
                case "Tutores legales" -> tutorService.deleteTutores(((List<TutorLegal>) ids));
                case "Alumnos" -> alumnoService.deleteAlumnos(((List<Alumno>) ids));
            }
            return tipo + " eliminados correctamente";
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void notifyUsersData(String ruta, List<AdminsUserData> adminsUserData, String userType) {
        try {
            // Obtenemos la ruta del directorio eliminando el nombre del archivo
            String directorio = obtenerDirectorio(ruta);

            // Creamos el BufferedWriter para escribir en el archivo CSV
            String tipoUser = "\\";
            if (userType.equalsIgnoreCase("Profesor")) {
                tipoUser += "profesores";
            } else if (userType.equalsIgnoreCase("Tutor")) {
                tipoUser += "tutores";
            } else if (userType.equalsIgnoreCase("Admin")) {
                tipoUser += "admins";
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio + tipoUser + LocalTime.now().getHour() + LocalTime.now().getMinute() + LocalTime.now().getSecond() + ".csv", true))) {
                // Escribimos cada objeto AdminsUserData en una línea del archivo CSV
                for (AdminsUserData userData : adminsUserData) {
                    writer.write(userData.getUsername() + ";" + userData.getPassword());
                    writer.newLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int comprobarFormatoNumero(String numero) {
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Dia esDiaValido(String dia) {
        try {
            return Dia.valueOf(dia);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean esCorreoValido(String correo) {
        // Define el patrón regex para una dirección de correo electrónico válida
        String regex = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);

        // Comprueba si el correo coincide con el patrón
        return pattern.matcher(correo).matches();
    }

    private String obtenerDirectorio(String ruta) {
        int lastIndex = ruta.lastIndexOf("\\");
        if (lastIndex != -1) {
            return ruta.substring(0, lastIndex);
        } else {
            return ruta;
        }
    }

    public String generarContrasena() {
        Random random = new Random();
        StringBuilder contrasena = new StringBuilder();

        // Generar entre 2 y 4 minúsculas
        int cantidadMinusculas = random.nextInt(3) + 2;
        for (int i = 0; i < cantidadMinusculas; i++) {
            char letra = (char) (random.nextInt(26) + 'a');
            contrasena.append(letra);
        }

        // Generar entre 2 y 4 mayúsculas
        int cantidadMayusculas = random.nextInt(3) + 2;
        for (int i = 0; i < cantidadMayusculas; i++) {
            char letra = (char) (random.nextInt(26) + 'A');
            contrasena.append(letra);
        }

        // Generar entre 1 y 3 números
        int cantidadNumeros = random.nextInt(3) + 1;
        for (int i = 0; i < cantidadNumeros; i++) {
            int numero = random.nextInt(10);
            contrasena.append(numero);
        }

        // Generar un símbolo aleatorio
        char[] simbolos = {'$', '%', '&', '/', '@', '¿', '?', '¡'};
        char simbolo = simbolos[random.nextInt(simbolos.length)];
        contrasena.append(simbolo);

        // Mezclar la contraseña para obtener un orden aleatorio de los caracteres
        for (int i = 0; i < contrasena.length(); i++) {
            int indiceAleatorio = random.nextInt(contrasena.length());
            char temp = contrasena.charAt(i);
            contrasena.setCharAt(i, contrasena.charAt(indiceAleatorio));
            contrasena.setCharAt(indiceAleatorio, temp);
        }

        return contrasena.toString();
    }

    public String generarUsername(String prefijo) {
        Random random = new Random();
        return prefijo + "_" + (random.nextInt(100) + 1);
    }

    public boolean comprobarDNI(String dni) {
        // Validar que el DNI tiene 9 caracteres
        if (dni == null || dni.length() != 9) {
            return false;
        }

        // Extraer los 8 primeros caracteres (el número)
        String numeroStr = dni.substring(0, 8);
        char letra = dni.charAt(8);

        // Validar que los 8 primeros caracteres son números
        if (!numeroStr.matches("\\d{8}")) {
            return false;
        }

        // Convertir el número a un entero
        int numero = Integer.parseInt(numeroStr);

        // Array de letras para el cálculo de la letra del DNI
        char[] letrasDNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        // Calcular la letra correspondiente
        char letraEsperada = letrasDNI[numero % 23];

        // Comparar la letra esperada con la letra proporcionada
        return letra == letraEsperada;
    }

    public static boolean comprobarNombre_Apellidos(String nombre) {
        // Normaliza el nombre para manejar caracteres con tildes y diacríticos
        String normalized = Normalizer.normalize(nombre, Normalizer.Form.NFD);
        // Elimina los caracteres de diacríticos dejando solo las letras base
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Define el patrón regex para letras, espacios en blanco y guiones
        String regex = "^[a-zA-Z\\s-]+$";
        Pattern pattern = Pattern.compile(regex);

        // Comprueba si el nombre normalizado coincide con el patrón
        return pattern.matcher(normalized).matches();
    }
}
