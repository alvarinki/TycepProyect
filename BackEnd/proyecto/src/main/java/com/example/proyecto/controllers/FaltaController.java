package com.example.proyecto.controllers;

import com.example.proyecto.dtos.FaltasCursoRequest;
import com.example.proyecto.model.Falta;
import com.example.proyecto.model.Horario;
import com.example.proyecto.services.*;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faltas")
public class FaltaController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    FaltaService faltaService;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    HorarioService horarioService;

    @Autowired
    AlumnoService alumnoService;

    @PostMapping("/fromAlumno")
    public ResponseEntity<Set<Falta>> getFaltasFromAlumno(@RequestBody int idAlumno, @RequestHeader String token) {
        jwtUtil.validate(token);
        Set<Falta> faltas = faltaService.findFaltasByIdAlumno(idAlumno);
        if (faltas != null) return ResponseEntity.ok(faltas);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/forAlumnos")
    public ResponseEntity<?> putFaltasToAlumnos(@RequestBody List<Falta> faltas, @RequestHeader String token) {
        jwtUtil.validate(token);
        faltaService.saveFaltas(faltas);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/deleteFromAlumno")
    public void deleteFaltaFromAlumno(@RequestBody Falta falta, @RequestHeader String token) {
        jwtUtil.validate(token);
        faltaService.deleteFalta(falta);
    }

    @PostMapping("/update")
    public ResponseEntity<Falta> updateFalta(@RequestBody Falta falta, @RequestHeader String token) {
        jwtUtil.validate(token);
        Falta f = faltaService.findFaltaById(falta.getId());
        if (f == null) return ResponseEntity.notFound().build();
        else {
            faltaService.updateFalta(f);
            return ResponseEntity.ok(f);
        }
    }

    @PostMapping("/fromCurso")
    public ResponseEntity<Set<Falta>> getFaltasFromCurso(@RequestBody FaltasCursoRequest faltasCursoRequest, @RequestHeader String token) {
        jwtUtil.validate(token);
        Set<Falta> faltas= new HashSet<>();
        int idCurso = faltasCursoRequest.getIdCurso();
        int idProfesor = faltasCursoRequest.getIdProfesor();
        System.out.println(idProfesor +" "+idCurso);
        if(idCurso == idProfesor){
            faltas= faltaService.findFaltasByIdCurso(faltasCursoRequest.getIdProfesor());
        }
        else{
            List<Horario> horarios=horarioService.findHorariosByIdProfesorAndIdCurso(idProfesor, idCurso);
            Set<String> asignaturas= horarios.stream().map(h -> h.getAsignatura().getNombre()).collect(Collectors.toSet());
            for(String asignatura: asignaturas){
                List<Integer> idsAlumnos= alumnoService.getAlumnosIdByIdCurso(idCurso);
                for(int id: idsAlumnos) {
                    List<Falta> faltasAlumno= faltaService.findFaltasByIdAlumnoAndAsignatura(id, asignatura);
                    System.out.println("FALTAS NULL");
                    if(faltasAlumno != null) {
                        faltas.addAll(faltasAlumno);

                    }
                }
            }
        }
        System.out.println(faltas);
        if(faltas != null) return ResponseEntity.ok(faltas);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/pruebaHorario/{idCurso}/{asignatura}")
    public ResponseEntity<?> probar (@PathVariable int idCurso, @PathVariable String asignatura) {
//        List<Horario> horarios=horarioService.findHorariosByIdProfesorAndIdCurso(idProfesor, idCurso);
//        Set<String> asignaturas= horarios.stream().map(h -> h.getAsignatura().getNombre()).collect(Collectors.toSet());
        List<Falta> faltas= new ArrayList<>();
        List<Integer> idsAlumnos= alumnoService.getAlumnosIdByIdCurso(1);
        for(int id: idsAlumnos) {
            List<Falta> faltasAlumno= faltaService.findFaltasByIdAlumnoAndAsignatura(id, asignatura);
            if(faltasAlumno != null) {
                faltas.addAll(faltasAlumno);
            }
        }
        return ResponseEntity.ok(faltas);
    }

}
