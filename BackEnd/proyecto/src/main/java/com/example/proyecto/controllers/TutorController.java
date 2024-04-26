package com.example.proyecto.controllers;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.services.TutorService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    TutorService tutorService;

    public ResponseEntity<Set<Alumno>> getAlumnosFromTutorLegal(int idTutor){
        TutorLegal tutorLegal= tutorService.findTutorLegalByUsuario_Id(idTutor);
        if(tutorLegal != null){
            Set<Alumno> alumnos = tutorLegal.getAlumnos();
            if(alumnos != null){
                return ResponseEntity.ok(alumnos);
            }
            else return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
