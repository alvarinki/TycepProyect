package com.example.proyecto.services;

import com.example.proyecto.model.Profesor;

public interface ProfesorService {

    Profesor findProfesorByUsuario_Id(Integer userId);

    Profesor saveProfesor(Profesor profesor);
}
