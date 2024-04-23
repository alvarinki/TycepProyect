package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Profesor;
import com.example.proyecto.repositories.ProfesorRepository;
import com.example.proyecto.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    ProfesorRepository profesorRepo;

    @Override
    public Profesor findProfesorByUsuario_Id(Integer userId) {
        return profesorRepo.findProfesorById(userId).orElse(null);
    }

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        return profesorRepo.save(profesor);
    }
}
