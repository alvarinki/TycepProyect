package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Curso;
import com.example.proyecto.repositories.CursoRepository;
import com.example.proyecto.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepo;


    @Override
    public Curso saveCurso(String nombreCurso) {
        return cursoRepo.save(new Curso(null, nombreCurso, null, null));
    }
}
