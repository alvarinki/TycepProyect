package com.example.proyecto.repositories;

import com.example.proyecto.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    Asignatura findByNombre(String nombre);
}
