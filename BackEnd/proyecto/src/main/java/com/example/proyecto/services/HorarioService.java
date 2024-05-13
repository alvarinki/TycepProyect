package com.example.proyecto.services;

import com.example.proyecto.model.Dia;
import com.example.proyecto.model.Horario;

import java.util.List;
import java.util.Set;

public interface HorarioService {

    Set<Horario> findHorariosByIdProfesorAndDia(int idProfesor, Dia dia);

    Set<Horario> findHorariosByIdCurso(int idCurso);

    Set<Horario> findHorariosByIdProfesor(int idProfesor);
}
