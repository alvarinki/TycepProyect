package com.example.proyecto.services.Impl;

import com.example.proyecto.repositories.CursoRepository;
import com.example.proyecto.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepo;


}
