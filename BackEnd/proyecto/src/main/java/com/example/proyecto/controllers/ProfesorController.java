package com.example.proyecto.controllers;

import com.example.proyecto.model.*;
import com.example.proyecto.services.*;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    HorarioService horarioService;


    @GetMapping("/cursos/{id}")
    public ResponseEntity<Set<Curso>> getCursosFromProfesor(@PathVariable int id) {
        Set<Curso> cursos = profesorService.getCursosFromProfesor(id);
        if (cursos != null) {
            return new ResponseEntity<>(cursos, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/curso/alumnos/{id}")
    public ResponseEntity<Set<Alumno>> getAlumnosFromProfesor(@PathVariable int id) {
        Set<Alumno> alumnos = alumnoService.findAlumnosByIdCurso(id);
        if (alumnos != null) {
            return new ResponseEntity<>(alumnos, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/horarioFaltas/{idProfesor}/{dia}")
    public ResponseEntity<Set<Horario>> getHorariosForFaltas(@PathVariable int idProfesor, @PathVariable Dia dia){
        Set<Horario> horarios=horarioService.findHorariosByIdProfesorAndDia(idProfesor, dia );
        if(horarios!=null&&!horarios.isEmpty()) return new ResponseEntity<>(horarios, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/horario/{idProfesor}")
    public ResponseEntity<Set<Horario>> getHorariosFromProfesor(@PathVariable int idProfesor){
        Set<Horario> horarios= horarioService.findHorariosByIdProfesor(idProfesor);
        if(horarios!=null&&!horarios.isEmpty()) return new ResponseEntity<>(horarios, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
