package com.example.proyecto.controllers;

import com.example.proyecto.model.Falta;
import com.example.proyecto.services.FaltaService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Set<Falta>> getFaltasFromAlumno(@RequestBody int idAlumno) {
        Set<Falta> faltas= faltaService.findFaltasByIdAlumno(idAlumno);
        if(faltas!=null) return ResponseEntity.ok(faltas);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/forAlumno")
    public ResponseEntity<?> putFaltasToAlumno(@RequestBody List<Falta> faltas) {
        if(faltaService.saveFaltas(faltas)!=null) return ResponseEntity.ok(faltas);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/deleteFromAlumno")
    public void deleteFaltaFromAlumno(@RequestBody Falta falta) {
        faltaService.deleteFalta(falta);
    }

    @PostMapping("/update")
    public ResponseEntity<Falta> updateFalta(@RequestBody Falta falta) {
        Falta f=faltaService.findFaltaById(falta.getId());
        if(f==null) return ResponseEntity.notFound().build();
        else {
            faltaService.updateFalta(f);
            return ResponseEntity.ok(f);
        }
    }
}
