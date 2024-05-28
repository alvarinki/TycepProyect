package com.example.proyecto.services;

import com.example.proyecto.model.Falta;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface FaltaService {
    Set<Falta> findFaltasByIdAlumno(int alumnoId);

    void saveFaltas(List<Falta> faltas);

    Falta updateFalta(Falta falta);

    Falta saveFalta(Falta falta);

    void deleteFalta(Falta falta);

    Falta findFaltaById(int id);

    Set<Falta> findFaltasByIdCurso(int idCurso);
}
