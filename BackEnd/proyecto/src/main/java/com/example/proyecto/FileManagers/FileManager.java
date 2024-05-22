package com.example.proyecto.FileManagers;

import com.example.proyecto.dtos.AdminsUserData;
import com.example.proyecto.model.*;
import com.example.proyecto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.*;
import java.time.LocalTime;
import java.util.*;

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

    public  List<Profesor> mapProfesores(String ruta) throws IOException {
        List<Profesor> profesores = new ArrayList<>();
        List<AdminsUserData> adminsUserData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    Profesor profesor = new Profesor();
                    profesor.setNombre(datos[0].trim());
                    profesor.setApellidos(datos[1].trim());
                    profesor.setDni(datos[2].trim());
                    profesor.setCorreo(datos[3].trim());
                    profesor.setContrasena(generarContrasena());
                    profesor.setDtype('P');
                    Arrays.stream(datos[4].split(",")).toList().forEach(asig -> profesor.getAsignaturas().add(asignaturaService.findByNombre(asig)));
                    String prefijo= profesor.getNombre().substring(0,1)+profesor.getApellidos().charAt(0)+profesor.getApellidos().split(" ")[1].charAt(0);
                    System.out.println(prefijo);
                    String nombreUsuario="";
                    do{
                        nombreUsuario= generarUsername(prefijo);
                    }while(usuarioService.findUsuarioByUsuario(nombreUsuario).isPresent());
                    profesor.setUsuario(nombreUsuario);
                    profesores.add(profesor);
                    adminsUserData.add(new AdminsUserData(nombreUsuario,profesor.getContrasena()));
                }
            }
        }
        notifyUsersData(ruta, adminsUserData, "Profesor");
        return profesores;
    }

    public  List<TutorLegal> mapTutores(String ruta) throws IOException {
        List<TutorLegal> tutores = new ArrayList<>();
        List<AdminsUserData> adminsUserData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    TutorLegal tutor= new TutorLegal();
                    tutor.setNombre(datos[0].trim());
                    tutor.setApellidos(datos[1].trim());
                    tutor.setTelefContacto(datos[2].trim());
                    tutor.setDomicilio(datos[3].trim());
                    tutor.setDtype('T');
                    tutor.setContrasena(generarContrasena());
                    Set<Alumno> alumnos= new HashSet<>();
                    String[] dnis = datos[4].split(",");
                    for (String dni : dnis) {
                        Optional<Alumno> a = alumnoService.findAlumnoByDni(dni);
                        a.ifPresent(alumnos::add);
                    }

                    //List<Alumno> alumnos= Arrays.stream(datos[4].split(",")).toList().stream().map(f -> alumnoService.findAlumnoByDni(f)).toList();
                    tutor.setAlumnos(alumnos);
                    String prefijo= tutor.getNombre().substring(0,1)+tutor.getApellidos().charAt(0)+tutor.getApellidos().split(" ")[1].charAt(0);

                    String nombreUsuario="";
                    do{
                        nombreUsuario= generarUsername(prefijo);
                    }while(usuarioService.findUsuarioByUsuario(nombreUsuario).isPresent());
                    tutor.setUsuario(nombreUsuario);
                    tutores.add(tutor);
                    adminsUserData.add(new AdminsUserData(nombreUsuario,tutor.getContrasena()));
                }
            }
        }
        notifyUsersData(ruta, adminsUserData, "Tutor");
        return tutores;
    }

    public  List<Usuario> mapAdmins(String ruta) throws IOException {
        List<Usuario> admins = new ArrayList<>();
        List<AdminsUserData> adminsUserData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    Usuario user= new Usuario();
                    user.setNombre(datos[0].trim());
                    user.setApellidos(datos[1].trim());
                    user.setContrasena(generarContrasena());

                    //List<Alumno> alumnos= Arrays.stream(datos[4].split(",")).toList().stream().map(f -> alumnoService.findAlumnoByDni(f)).toList();

                    String prefijo= user.getNombre().substring(0,1)+user.getApellidos().charAt(0)+user.getApellidos().split(" ")[1].charAt(0);;

                    String nombreUsuario="";
                    do{
                        nombreUsuario= generarUsername(prefijo);
                    }while(usuarioService.findUsuarioByUsuario(nombreUsuario).isPresent());
                    user.setUsuario(nombreUsuario);
                    admins.add(user);
                    adminsUserData.add(new AdminsUserData(nombreUsuario,user.getContrasena()));
                }
            }
        }
        notifyUsersData(ruta, adminsUserData, "Admin");
        return admins;
    }


    public String mapAlumnos(String ruta) {
        List<Alumno> alumnos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea=1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Alumno alumno= new Alumno();
                alumno.setNombre(datos[0].trim());
                alumno.setApellidos(datos[1].trim());
                int idCurso= cursoService.findCursoByNombre(datos[2].trim());
                if(idCurso!=0) alumno.setIdCurso(idCurso);
                else{
                    return "Hay un error en la introducción del curso en la línea "+numeroLinea;
                }
                if(comprobarDNI(datos[3].trim())) alumno.setDni(datos[3].trim());


                else {
                    return "El dni introducido en la línea "+numeroLinea+" no existe";
                }

                if(alumnoService.findAlumnoByDni(alumno.getDni()).isEmpty()) alumnos.add(alumno);;
                numeroLinea++;
            }
            alumnoService.saveAlumnos(alumnos);
            return "Alumnos registrados correctamente";
        }

        catch (IOException e) {
           throw new RuntimeException(e);
        }

    }

    public String mapHorarios(String ruta) {
        List<Horario> horarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea=1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Horario horario= new Horario();

                Asignatura asignatura= asignaturaService.findByNombre(datos[0].trim());
                if(asignatura!=null) horario.setAsignatura(asignatura);
                else return "Error en la introducción de la asignatura en la línea "+numeroLinea;

                Dia dia = esDiaValido(datos[1].trim().toUpperCase());
                if(dia!=null) horario.setDia(dia);
                else return "Error en la introducción del día en la línea "+numeroLinea;

                int hora= comprobarFormatoNumero(datos[1].trim());
                if(hora!=0) horario.setHora(hora);
                else return "Error en el formato de la hora en la línea "+numeroLinea;

                int idCurso= cursoService.findCursoByNombre(datos[3].trim());
                if(idCurso!=0) horario.setIdCurso(idCurso);
                else return "Hay un error en la introducción del curso en la línea "+numeroLinea;

                int aula=comprobarFormatoNumero(datos[4].trim());
                if(aula!=0) horario.setAula(aula);
                else return "Error en el formato del aula en la línea "+numeroLinea;


                int idProfesor= profesorService.findProfesorByDni(datos[5].trim());

                if(idProfesor!=0) horario.setIdProfesor(idProfesor);
                else return "El dni introducido en la línea "+numeroLinea+" no corresponde a ningún profesor";

               horarios.add(horario);
//                Tendría que hacer esto también!!
//                if(alumnoService.findAlumnoByDni(alumno.getDni()).isEmpty()) alumnos.add(alumno);;
                numeroLinea++;
            }

            horarioService.saveHorarios(horarios);
            return "Alumnos registrados correctamente";
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int comprobarFormatoNumero(String numero) {
        try {
            return Integer.parseInt(numero);
        }
        catch (NumberFormatException e) {
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

    public  void notifyUsersData(String ruta, List<AdminsUserData> adminsUserData, String userType){
        try {
            // Obtenemos la ruta del directorio eliminando el nombre del archivo
            String directorio = obtenerDirectorio(ruta);

            // Creamos el BufferedWriter para escribir en el archivo CSV
            String tipoUser = "\\";
            if(userType.equalsIgnoreCase("Profesor")){
                tipoUser += "profesores";
            }
            else if (userType.equalsIgnoreCase("Tutor")){
                tipoUser += "tutores";
            }
            else if(userType.equalsIgnoreCase("Admin")){
                tipoUser += "admins";
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio + tipoUser +LocalTime.now().getHour()+LocalTime.now().getMinute()+LocalTime.now().getSecond() +".csv", true))) {
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


    private String obtenerDirectorio(String ruta) {
        int lastIndex = ruta.lastIndexOf("\\");
        if (lastIndex != -1) {
            return ruta.substring(0, lastIndex);
        } else {
            return ruta; // Si no se encuentra ningún separador de directorios, se asume que la ruta ya es un directorio
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

    public String generarUsername(String prefijo){
        Random random = new Random();
        return prefijo+"_"+(random.nextInt(100)+1);
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
}
