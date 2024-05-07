package com.example.proyecto.services;

import com.example.proyecto.model.Asignatura;

public interface AsignaturaService {

    Asignatura findByNombre(String nombre);
}
