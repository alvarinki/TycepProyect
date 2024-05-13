package com.example.proyecto.repositories;

import com.example.proyecto.model.Dia;
import com.example.proyecto.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Optional<Set<Horario>> findHorariosByIdProfesorAndDia(int idProfesor, Dia dia);

    Optional<Set<Horario>> findHorariosByIdCurso(int idCurso);

    Optional<Set<Horario>> findHorariosByIdProfesor(int idProfesor);
}
