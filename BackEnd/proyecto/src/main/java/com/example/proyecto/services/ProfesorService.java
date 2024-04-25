package com.example.proyecto.services;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Curso;
import com.example.proyecto.model.Profesor;

import java.util.Set;

public interface ProfesorService {

    Profesor findProfesorByUsuario_Id(Integer userId);

    Profesor saveProfesor(Profesor profesor);

    Set<Curso> getCursosFromProfesor(int id);

}
