package com.example.proyecto.services;

import com.example.proyecto.model.Alumno;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AlumnoService {

    Set<Alumno> findAlumnosByIdCurso(int idCurso);

    Alumno findAlumnoById(int id);

    Optional<Alumno> findAlumnoByNombre(String nombre);

    Alumno findAlumnoByDni(String dni);

    int getIdAlumnoByDni(String dni);

    void saveAlumnos(List<Alumno> alumnos);

    void deleteAlumnos(List<Alumno> alumnos);

    void saveFotoFromAlumno(int idAlumno, String foto);

    void deleteAlumnoById(int idAlumno);
}
