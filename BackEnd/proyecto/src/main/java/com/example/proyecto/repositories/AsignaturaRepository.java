package com.example.proyecto.repositories;

import com.example.proyecto.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    Optional<Asignatura> findByNombre(String nombre);
}
