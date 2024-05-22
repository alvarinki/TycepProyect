package com.example.proyecto.services;

import com.example.proyecto.model.Curso;

public interface CursoService {

    Curso saveCurso(String nombreCurso);

    int findCursoByNombre(String nombre);
}
