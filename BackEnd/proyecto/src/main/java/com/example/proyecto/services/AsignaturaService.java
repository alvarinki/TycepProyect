package com.example.proyecto.services;

import com.example.proyecto.model.Asignatura;

import java.util.List;

public interface AsignaturaService {

    Asignatura findByNombre(String nombre);

    void saveAsignaturas(List<Asignatura> asignaturas);
}
