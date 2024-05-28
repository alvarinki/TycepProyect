package com.example.proyecto.repositories;

import com.example.proyecto.model.Falta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FaltaRepository extends JpaRepository<Falta, Integer> {

    @Query("from Falta where idAlumno = :alumnoId")
    List<Falta> findFaltasByIdAlumno(@Param("alumnoId") int alumnoId);

    Falta findFaltaById(int id);

    Falta findFaltaByHoraAndIdAlumnoAndFecha(int hora, int idAlumno, LocalDate fecha);

    @Query("SELECT f FROM Falta f WHERE f.idAlumno IN (SELECT a.id FROM Alumno a WHERE a.idCurso = :idCurso)")
    Optional<Set<Falta>> findFaltasByIdCurso(int idCurso);
}
