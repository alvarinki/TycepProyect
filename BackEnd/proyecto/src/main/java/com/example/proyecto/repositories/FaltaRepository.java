package com.example.proyecto.repositories;

import com.example.proyecto.model.Falta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FaltaRepository extends JpaRepository<Falta, Integer> {

    @Query("from Falta where idAlumno = :alumnoId")
    List<Falta> findFaltasByIdAlumno(@Param("alumnoId") int alumnoId);

    Falta findFaltaById(int id);
}
