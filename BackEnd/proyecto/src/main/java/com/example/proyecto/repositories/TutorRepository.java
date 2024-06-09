package com.example.proyecto.repositories;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.TutorLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<TutorLegal, Integer> {

    Optional<TutorLegal> findTutorLegalById(Integer userId);

    Optional<TutorLegal> findTutorLegalByDni(String dni);

    Optional<Integer> getIdByDni(String dni);

    Optional<TutorLegal> findTutorLegalByAlumnosContaining(Alumno alumno);

    @Query("SELECT CONCAT(t.nombre,' ',t.apellidos, '_', t.usuario) " +
            "FROM TutorLegal t JOIN t.alumnos a " +
            "WHERE a.id = :alumnoId")
    List<String> findTutoresByIdAlumno(@Param("alumnoId") Integer alumnoId);
}
