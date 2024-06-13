package com.example.proyecto.controllers;

import com.example.proyecto.dtos.AlumnoDto;
import com.example.proyecto.dtos.TutorDataDto;
import com.example.proyecto.model.Alumno;
import com.example.proyecto.services.AlumnoService;
import com.example.proyecto.services.TutorService;
import com.example.proyecto.util.JWTUtil;
import io.swagger.v3.oas.annotations.headers.Header;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    TutorService tutorService;

    @PostMapping("/getAlumno")
    public ResponseEntity<AlumnoDto> getAlumnoById(@RequestBody int id, @RequestHeader String token) {
        jwtUtil.validate(token);
        Alumno alumno = alumnoService.findAlumnoById(id);


        if (alumno != null){
            List<String> tutores= tutorService.findTutoresByIdAlumno(alumno.getId());
            AlumnoDto alumnoDto = new AlumnoDto(alumno, tutores);
            return ResponseEntity.ok(alumnoDto);
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/getTutoresFromAlumno")
    public ResponseEntity<List<TutorDataDto>> getTutoresFromAlumno(@RequestBody int idAlumno){
        List<String> tutores = tutorService.findTutoresByIdAlumno(idAlumno);
        List<TutorDataDto> tutoresFormat= tutores.stream().map(tutor -> new TutorDataDto(tutor.split("-")[0], tutor.split("-")[1])).toList();
        System.out.println(tutoresFormat.toString());
        if(tutores != null) return ResponseEntity.ok(tutoresFormat);
        else return ResponseEntity.notFound().build();
    }
}
