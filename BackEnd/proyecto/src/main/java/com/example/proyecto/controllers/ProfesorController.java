package com.example.proyecto.controllers;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Curso;
import com.example.proyecto.model.Profesor;
import com.example.proyecto.services.AlumnoService;
import com.example.proyecto.services.CursoService;
import com.example.proyecto.services.FaltaService;
import com.example.proyecto.services.ProfesorService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.Set;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    CursoService cursoService;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    FaltaService faltaService;

    @GetMapping("/cursos/{id}")
    public ResponseEntity<Set<Curso>> getCursosFromProfesor(@PathVariable int id){
        Set<Curso> cursos = profesorService.getCursosFromProfesor(id);
        if(cursos != null){
            return new ResponseEntity<>(cursos, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/curso/alumnos/{id}")
    public ResponseEntity<Set<Alumno>> getAlumnosFromProfesor(@PathVariable int id){
        Set<Alumno> alumnos= alumnoService.findAlumnosByIdCurso(id);
        if(alumnos != null){
            return new ResponseEntity<>(alumnos, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
