package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Dia;
import com.example.proyecto.model.Horario;
import com.example.proyecto.repositories.HorarioRepository;
import com.example.proyecto.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    HorarioRepository horarioRepo;

    @Override
    public Set<Horario> findHorariosByIdProfesorAndDia(int idProfesor, Dia dia) {
        return horarioRepo.findHorariosByIdProfesorAndDia(idProfesor, dia).orElse(null);
    }

    @Override
    public Set<Horario> findHorariosByIdCurso(int idCurso) {
        return horarioRepo.findHorariosByIdCurso(idCurso).orElse(null);
    }

    @Override
    public Set<Horario> findHorariosByIdProfesor(int idProfesor) {
        return horarioRepo.findHorariosByIdProfesor(idProfesor).orElse(null);
    }

    @Override
    public void saveHorarios(List<Horario> horarios) {
        horarioRepo.saveAll(horarios);
    }

}
