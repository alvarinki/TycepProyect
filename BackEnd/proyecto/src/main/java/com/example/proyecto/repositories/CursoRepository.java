package com.example.proyecto.repositories;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findById(int id);


}
