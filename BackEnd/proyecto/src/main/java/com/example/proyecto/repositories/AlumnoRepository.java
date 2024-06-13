package com.example.proyecto.repositories;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Falta;
import com.example.proyecto.model.TutorLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    Set<Alumno> getAlumnosByIdCurso(int id);

    Optional<Alumno> findAlumnoById(int id);

    Optional<Alumno> findAlumnoByNombre(String nombre);

   Optional<Alumno> findAlumnoByDni(String dni);

   Optional<Integer> getIdAlumnoByDni(String dni);

   @Query("Select a.id from Alumno a where a.idCurso= :idCurso")
    Optional<List<Integer>> getAlumnosIdByIdCurso(int idCurso);
}
