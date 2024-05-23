package com.example.proyecto.services;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Curso;
import com.example.proyecto.model.Profesor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProfesorService {

    Profesor findProfesorByUsuario_Id(Integer userId);

    Profesor saveProfesor(Profesor profesor);

    Set<Curso> getCursosFromProfesor(int id);

    int findProfesorByDni(String dni);

    void saveProfesores(List<Profesor> profesores);

    void deleteProfesores(List<Profesor> profesores);
}
