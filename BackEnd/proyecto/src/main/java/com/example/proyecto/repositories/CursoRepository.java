package com.example.proyecto.repositories;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findById(int id);

    Optional<Curso> findCursoByNombre(String nombre);
}
