package com.example.proyecto.controllers;

import com.example.proyecto.model.Falta;
import com.example.proyecto.services.FaltaService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/faltas")
public class FaltaController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    FaltaService faltaService;

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
    public ResponseEntity<Set<Falta>> getFaltasFromCurso(@RequestBody int idCurso, @RequestHeader String token) {
        jwtUtil.validate(token);
        Set<Falta> faltas= faltaService.findFaltasByIdCurso(idCurso);
        if(faltas != null) return ResponseEntity.ok(faltas);
        else return ResponseEntity.notFound().build();
    }
}
