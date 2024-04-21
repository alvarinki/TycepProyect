package com.example.proyecto.repositories;

import com.example.proyecto.model.Profesor;
import com.example.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {

    Profesor findProfesorById(Integer userId);
}
