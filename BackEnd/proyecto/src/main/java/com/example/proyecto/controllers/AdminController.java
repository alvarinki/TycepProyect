package com.example.proyecto.controllers;

import com.example.proyecto.FileManagers.FileManager;
import com.example.proyecto.model.Curso;
import com.example.proyecto.model.Profesor;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.model.Usuario;
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
    AsignaturaService asignaturaService;

    @Autowired
    CursoService cursoService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private FileManager fileManager;

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
            if(user.getDtype().toString().equals("A")){
                return new ResponseEntity<>(usuarioService.saveUsuario(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/registerProfesores")
    public ResponseEntity<?> registerProfesores(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);
        try {
            List<Profesor> profesores= fileManager.mapProfesores(ruta);
            return ResponseEntity.ok(profesores);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PostMapping("/registerTutores")
    public ResponseEntity<?> registerTutores(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);
        try {
            List<TutorLegal> tutores= fileManager.mapTutores(ruta);
            return ResponseEntity.ok(tutores);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PostMapping("/registerAdmins")
    public ResponseEntity<?> registerAdmins(@RequestBody String ruta, @RequestHeader String token){
        jwtUtil.validate(token);
        try {
            List<Usuario> admins= fileManager.mapAdmins(ruta);
            return ResponseEntity.ok(admins);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PostMapping("/saveCurso")
    public ResponseEntity<Curso> saveCurso(@RequestBody String nombreCurso, @RequestHeader String token){
        jwtUtil.validate(token);
        try{
            Curso curso= cursoService.saveCurso(nombreCurso);
            if(curso!=null) return new ResponseEntity<>(curso, HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
