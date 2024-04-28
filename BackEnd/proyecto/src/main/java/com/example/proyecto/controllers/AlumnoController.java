package com.example.proyecto.controllers;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.services.AlumnoService;
import com.example.proyecto.util.JWTUtil;
import io.swagger.v3.oas.annotations.headers.Header;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AlumnoService alumnoService;

    @PostMapping("/getAlumno")
    public ResponseEntity<Alumno> getAlumnoById(@RequestBody int id, @RequestHeader String token){
        jwtUtil.validate(token);
        Alumno alumno= alumnoService.findAlumnoById(id);
        if(alumno!=null) return ResponseEntity.ok(alumno);
        else return ResponseEntity.notFound().build();
    }
}
