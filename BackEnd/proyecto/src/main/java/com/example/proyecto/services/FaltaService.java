package com.example.proyecto.services;

import com.example.proyecto.model.Falta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("select f from Falta f where f.idAlumno= :idAlumno and f.asignatura= :nombreAsignatura ")
    List<Falta> findFaltasByIdAlumnoAndAsignatura(@Param("idAlumno") Integer idAlumno, @Param("nombreAsignatura") String nombreAsignatura);
}
