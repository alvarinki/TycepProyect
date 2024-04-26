package com.example.proyecto.repositories;

import com.example.proyecto.model.Falta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FaltaRepository extends JpaRepository<Falta, Integer> {

    Set<Falta> findFaltasByIdAlumno_Id(int alumnoId);

    Falta findFaltaById(int id);
}
