package com.example.proyecto.services;

import com.example.proyecto.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    Curso saveCurso(String nombreCurso);

    int findCursoByNombre(String nombre);

    Curso getCursoByNombre(String nombre);

    void saveCursos(List<Curso> cursos);
}
