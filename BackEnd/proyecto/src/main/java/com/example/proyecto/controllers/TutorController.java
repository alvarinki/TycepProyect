package com.example.proyecto.controllers;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Horario;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.services.HorarioService;
import com.example.proyecto.services.TutorService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    TutorService tutorService;

    @Autowired
    HorarioService horarioService;

    @PostMapping("/getAlumnos")
    public ResponseEntity<Set<Alumno>> getAlumnosFromTutorLegal(@RequestBody int idTutor, @RequestHeader String token) {
        jwtUtil.validate(token);
        TutorLegal tutorLegal = tutorService.findTutorLegalByUsuario_Id(idTutor);
        if (tutorLegal != null) {
            Set<Alumno> alumnos = tutorLegal.getAlumnos();
            if (alumnos != null) {
                System.out.println("Se enviaron bien "+ alumnos );
                return ResponseEntity.ok(alumnos);
            } else return ResponseEntity.notFound().build();
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping("/getHorarioFromAlumno")
    public ResponseEntity<Set<Horario>> getHorarioFromAlumno(@RequestBody int idCurso, @RequestHeader String token) {
        jwtUtil.validate(token);
        Set<Horario> horarioAlumno= horarioService.findHorariosByIdCurso(idCurso);
        if(horarioAlumno != null) return ResponseEntity.ok(horarioAlumno);
        else return ResponseEntity.notFound().build();
    }
}
