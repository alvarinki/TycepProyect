package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Asignatura;
import com.example.proyecto.repositories.AsignaturaRepository;
import com.example.proyecto.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    @Autowired
    AsignaturaRepository asignaturaRepo;

    @Override
    public Asignatura findByNombre(String nombre) {
        return asignaturaRepo.findByNombre(nombre).orElse(null);
    }

    @Override
    public void saveAsignaturas(List<Asignatura> asignaturas) {
        asignaturaRepo.saveAll(asignaturas);
    }
}
