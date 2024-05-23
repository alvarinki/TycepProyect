package com.example.proyecto.controllers;

import com.example.proyecto.FileManagers.FileManager;
import com.example.proyecto.model.*;
import com.example.proyecto.services.*;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private FileManager fileManager;



    @PostMapping("/registerProfesores")
    public ResponseEntity<String> registerProfesores(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);
        String mensaje= fileManager.mapProfesores(ruta);
        if(mensaje.startsWith("Profesores registrados correctamente")) return ResponseEntity.ok(mensaje);
        else return ResponseEntity.badRequest().body(mensaje);

    }

    @PostMapping("/registerTutores")
    public ResponseEntity<?> registerTutores(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);
        String mensaje = fileManager.mapTutores(ruta);
        if(mensaje.startsWith("Tutores registrados correctamente")) return ResponseEntity.ok(mensaje);
        else return ResponseEntity.badRequest().body(mensaje);
    }

    @PostMapping("/registerAdmins")
    public ResponseEntity<String> registerAdmins(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);
        String mensaje = fileManager.mapAdmins(ruta);

        if(mensaje.startsWith("Admins registrados correctamente")) return ResponseEntity.ok(mensaje);
        else return ResponseEntity.badRequest().body(mensaje);

    }

    @PostMapping("/registerAlumnos")
    public ResponseEntity<String> registerAlumnos(@RequestBody String ruta, @RequestHeader String token){
        //Comentado de forma momentánea para pruebas
        jwtUtil.validate(token);
        String mensaje= fileManager.mapAlumnos(ruta);

        if(mensaje.startsWith("Alumnos registrados correctamente")) return ResponseEntity.ok(mensaje);
        else return ResponseEntity.badRequest().body(mensaje);
    }

    @PostMapping("/saveCurso")
    public ResponseEntity<Curso> saveCurso(@RequestBody String nombreCurso, @RequestHeader String token){
        jwtUtil.validate(token);
        Curso curso= cursoService.saveCurso(nombreCurso);

        if(curso!=null) return ResponseEntity.ok(curso);
        else return new ResponseEntity<>(HttpStatus.FOUND);

    }

    @PostMapping("/registerHorarios")
    public ResponseEntity<String> registerHorarios(@RequestBody String ruta, @RequestHeader String token){
        //Comentado para pruebas
        jwtUtil.validate(token);
        String mensaje= fileManager.mapHorarios(ruta);

        if(mensaje.startsWith("Horarios registrados correctamente")) return ResponseEntity.ok(mensaje);
        else return ResponseEntity.badRequest().body(mensaje);
    }

    @PostMapping("/deleteUsers")
    public ResponseEntity<String> deleteUsers(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);

    }


    //Métodos momentáneos para inserción de datos de prueba
    @PostMapping("/registerTutor")
    public ResponseEntity<TutorLegal> tutorRegistration(@RequestBody TutorLegal tutor, @RequestHeader String token){
        jwtUtil.validate(token);
        if(tutor!=null){
            if(tutor.getDtype().toString().equals("T")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                System.out.println(tutor.getContrasena());
                tutor.setContrasena(passwordEncoder.encode(tutor.getContrasena()));
                TutorLegal tutorLegal = tutorService.saveTutor(tutor);
                return new ResponseEntity<>(tutorLegal, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/registerProfesor")
    public ResponseEntity<Profesor> teacherRegistration(@RequestBody Profesor profe, @RequestHeader String token){
        jwtUtil.validate(token);
        if(profe!=null){
            if(profe.getDtype().toString().equals("P")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                profe.setContrasena(passwordEncoder.encode(profe.getContrasena()));
                Profesor profesor = profesorService.saveProfesor(profe);
                return new ResponseEntity<>(profesor, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<Usuario> userRegistration(@RequestBody Usuario user, @RequestHeader String token){
        jwtUtil.validate(token);
        if(user!=null){
            if(user.getDtype().toString().equals("A")) return new ResponseEntity<>(usuarioService.saveUsuario(user), HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
