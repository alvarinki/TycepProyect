import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileManager {

    public static List<Profesor> mapProfesores(String ruta) throws IOException {
        List<Profesor> profesores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 3) {
                    Profesor profesor = new Profesor();
                    profesor.setNombre(datos[0].trim());
                    profesor.setApellidos(datos[1].trim());
                    profesor.setDni(datos[2].trim());
                    profesor.setCorreo(datos[3].trim());
                    profesor.setContrasena(generarContrasena());
                    profesores.add(profesor);
                }
            }
        }
        return profesores;
    }

    public static boolean notifyUsersData(String ruta, List<AdminsUserData> adminsUserData){
        try {
            // Obtenemos la ruta del directorio eliminando el nombre del archivo
            String directorio = obtenerDirectorio(ruta);

            // Creamos el BufferedWriter para escribir en el archivo CSV
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio + "\\usuarios.csv", true))) {
                // Escribimos cada objeto AdminsUserData en una línea del archivo CSV
                for (AdminsUserData userData : adminsUserData) {
                    writer.write(userData.getUsername() + ";" + userData.getPassword());
                    writer.newLine();
                }
                return true; // Notificación exitosa
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error al escribir en el archivo
        }
    }


    private static String obtenerDirectorio(String ruta) {
        int lastIndex = ruta.lastIndexOf("\\");
        if (lastIndex != -1) {
            return ruta.substring(0, lastIndex);
        } else {
            return ruta; // Si no se encuentra ningún separador de directorios, se asume que la ruta ya es un directorio
        }
    }

    public static String generarContrasena() {
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
}
