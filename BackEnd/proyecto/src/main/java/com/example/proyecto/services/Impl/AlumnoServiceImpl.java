package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.repositories.AlumnoRepository;
import com.example.proyecto.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepo;

    @Override
    public Set<Alumno> findAlumnosByIdCurso(int idCurso) {
        Set<Alumno> alumnos= alumnoRepo.getAlumnosByIdCurso(idCurso);
        if(alumnos!=null) return alumnoRepo.getAlumnosByIdCurso(idCurso);
        else return null;
    }
}
