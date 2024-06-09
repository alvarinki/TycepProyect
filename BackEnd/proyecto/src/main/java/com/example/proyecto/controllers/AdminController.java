package com.example.proyecto.controllers;

import com.example.proyecto.FileManagers.FileManager;
import com.example.proyecto.dtos.AdminsUserData;
import com.example.proyecto.model.*;
import com.example.proyecto.modelFB.ChatFB;
import com.example.proyecto.modelFB.UsuarioFB;
import com.example.proyecto.services.*;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TutorService tutorService;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    AsignaturaService asignaturaService;

    @Autowired
    CursoService cursoService;

    private final FirebaseService firebaseService;

    public AdminController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }
//    @Autowired
//    FirebaseService firebaseService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private FileManager fileManager;

    @GetMapping("/deleteP/{id}")
    public ResponseEntity<?> deleteP(@PathVariable("id") int id) {

        profesorService.deleteProfesorById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type, @RequestHeader("authorization") String token) {
        System.out.println(token);
        if (jwtUtil.validate(token)) {
            String nombreUsuario = jwtUtil.getValue(token);
            if (comprobarAdmin(usuarioService.findDTypeFromUsuarioByUsuario(nombreUsuario))) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("File is empty");
                }
                switch (type) {

                    case "Profesores" -> {
                        Object o = fileManager.mapProfesores(file);
                        ;
                        return comprobarRespuestaRegisters(o);
                    }

                    case "Tutores legales" -> {
                        Object o = fileManager.mapTutores(file);
                        System.out.println((String) o);
                        return comprobarRespuestaRegisters(o);
                    }

                    case "Administradores" -> {
                        Object o = fileManager.mapAdmins(file);
                        return comprobarRespuestaRegisters(o);
                    }

                    case "Asignaturas" -> {

                    }

                    case "Horarios" -> {
//                        Object o = fileManager.mapHorarios(file);
//                        return comprobarRespuestaRegisters(o);
                    }
                    case "Alumnos" -> {
                        String mensaje = fileManager.mapAlumnos(file);
                        if (mensaje.startsWith("Error")) {
                            return ResponseEntity.badRequest().body(mensaje);
                        } else return ResponseEntity.ok().body(mensaje);
                    }
                }
                List<String> users = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);

                        users.add(line); // Aquí puedes agregar lógica para procesar y guardar usuarios en la BD
                    }
                } catch (IOException e) {
                    return ResponseEntity.status(500).body("Error reading file");
                }

                return ResponseEntity.ok("File processed successfully with " + users.size() + " users.");
            } else return ResponseEntity.badRequest().body("El usuario no es un administrador");
        } else {
            return new ResponseEntity<>("Token inválido o caducado", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> comprobarRespuestaRegisters(Object o) {
        if (o instanceof String) {
            return ResponseEntity.badRequest().body((String) o);
        } else {
            return ResponseEntity.ok((List<AdminsUserData>) o);
        }
    }

//    @PostMapping("/registerProfesores")
//    public Object registerProfesores(MultipartFile file) {
//        System.out.println(file.getOriginalFilename());
////        if (jwtUtil.validate(token)) {
////            String nombreUsuario = jwtUtil.getValue(token);
////            Optional<Usuario> user= usuarioService.findUsuarioByUsuario(nombreUsuario);
////            if (comprobarAdmin(usuarioService.findDTypeFromUsuarioByUsuario(nombreUsuario))) {
//
//        return fileManager.mapProfesores(file);
//

//            } else return ResponseEntity.badRequest().body("El usuario no es un administrador");
//        } else return new ResponseEntity<>("Token inválido o caducado", HttpStatus.UNAUTHORIZED);
//    }

//    @PostMapping("/registerTutores")
//    public ResponseEntity<?> registerTutores(MultipartFile file) {
//                String mensaje = fileManager.mapTutores(file);
//                if (mensaje.startsWith("Tutores registrados correctamente")) return ResponseEntity.ok(mensaje);
//                else return ResponseEntity.badRequest().body(mensaje);
//    }
//
//
//    @PostMapping("/registerAdmins")
//    public ResponseEntity<String> registerAdmins(MultipartFile file) {
//                String mensaje = fileManager.mapAdmins(file);
//                if (mensaje.startsWith("Admins registrados correctamente")) return ResponseEntity.ok(mensaje);
//                else return ResponseEntity.badRequest().body(mensaje);
//    }

//    @PostMapping("/registerAlumnos")
//    public String registerAlumnos(MultipartFile file) {
//                String mensaje = fileManager.mapAlumnos(file);
//                if (mensaje.startsWith("Alumnos registrados correctamente")) return mensaje;
//                else return mensaje;
//    }

    @PostMapping("/saveCurso")
    public ResponseEntity<Curso> saveCurso(@RequestBody String nombreCurso, @RequestHeader String token) {

            Curso curso = cursoService.saveCurso(nombreCurso);
            if (curso != null) return ResponseEntity.ok(curso);
            else return new ResponseEntity<>(HttpStatus.FOUND);

    }

//    @PostMapping("/registerHorarios")
//    public ResponseEntity<String> registerHorarios(MultipartFile file) {
//                String mensaje = fileManager.mapHorarios(file);
//                if (mensaje.startsWith("Horarios registrados correctamente")) return ResponseEntity.ok(mensaje);
//                else return ResponseEntity.badRequest().body(mensaje);
//    }

    @PostMapping("/deleteUsersOrStudents")
    public ResponseEntity<String> deleteUsersOrStudents(String ruta, @RequestHeader String token) {
        if (jwtUtil.validate(token)) {
            String nombreUsuario = jwtUtil.getValue(token);
            if (comprobarAdmin(usuarioService.findDTypeFromUsuarioByUsuario(nombreUsuario))) {
                String mensaje = fileManager.deleteByDniList(ruta);
                if (!mensaje.startsWith("Error")) return ResponseEntity.ok(mensaje);
                else return ResponseEntity.badRequest().body(mensaje);
            } else return ResponseEntity.badRequest().body("El usuario no es un administrador");
        } else return new ResponseEntity<>("Token inválido o caducado", HttpStatus.UNAUTHORIZED);
    }

    private boolean comprobarAdmin(Character dType) {
        return dType == 'A';
    }

    //Métodos momentáneos para inserción de datos de prueba

    @PostMapping("/registerTutor")
    public ResponseEntity<TutorLegal> tutorRegistration(@RequestBody TutorLegal tutor, @RequestHeader String token) {
        jwtUtil.validate(token);
        if (tutor != null) {
            if (tutor.getDtype().toString().equals("T")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                tutor.setContrasena(passwordEncoder.encode(tutor.getContrasena()));
                TutorLegal tutorLegal = tutorService.saveTutor(tutor);
                return new ResponseEntity<>(tutorLegal, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/registerProfesor")
    public ResponseEntity<Profesor> teacherRegistration(@RequestBody Profesor profe, @RequestHeader String token) {
        jwtUtil.validate(token);
        if (profe != null) {
            if (profe.getDtype().toString().equals("P")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                profe.setContrasena(passwordEncoder.encode(profe.getContrasena()));
                Profesor profesor = profesorService.saveProfesor(profe);
                return new ResponseEntity<>(profesor, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<Usuario> userRegistration(@RequestBody Usuario user, @RequestHeader String token) {
        jwtUtil.validate(token);
        if (user != null) {
            if (user.getDtype().toString().equals("A")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                user.setContrasena(passwordEncoder.encode(user.getContrasena()));
                return new ResponseEntity<>(usuarioService.saveUsuario(user), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/prueba")
    public void verUsuariosdeChat(@RequestBody List<UsuarioFB> usuariosFB) {
        firebaseService.guardarUsuarios(usuariosFB);
    }


}