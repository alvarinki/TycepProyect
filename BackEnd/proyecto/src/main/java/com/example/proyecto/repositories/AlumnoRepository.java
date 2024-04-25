package com.example.proyecto.repositories;

import com.example.proyecto.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    Set<Alumno> getAlumnosByIdCurso(int id);
}
