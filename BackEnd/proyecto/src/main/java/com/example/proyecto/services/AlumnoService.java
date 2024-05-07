package com.example.proyecto.services;

import com.example.proyecto.model.Alumno;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AlumnoService {

    Set<Alumno> findAlumnosByIdCurso(int idCurso);

    Alumno findAlumnoById(int id);

    Optional<Alumno> findAlumnoByNombre(String nombre);

    Optional<Alumno> findAlumnoByDni(String dni);
}
