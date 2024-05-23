package com.example.proyecto.services;

import com.example.proyecto.model.Dia;
import com.example.proyecto.model.Horario;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HorarioService {

    Set<Horario> findHorariosByIdProfesorAndDia(int idProfesor, Dia dia);

    Set<Horario> findHorariosByIdCurso(int idCurso);

    Set<Horario> findHorariosByIdProfesor(int idProfesor);

    void saveHorarios(List<Horario> horarios);

    Horario findHorarioByDiaAndHoraAndIdCurso(Dia dia, int hora, int idCurso);

    void deleteHorario(Horario horario);
}
