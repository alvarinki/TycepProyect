package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Curso;
import com.example.proyecto.repositories.CursoRepository;
import com.example.proyecto.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepo;

    @Override
    public int findCursoByNombre(String nombre) {
        Optional<Curso> curso= cursoRepo.findCursoByNombre(nombre);
        if(curso.isPresent()) return curso.get().getId();
        return 0;
    }

    @Override
    public Curso getCursoByNombre(String nombre) {
        return cursoRepo.findCursoByNombre(nombre).orElse(null);
    }

    @Override
    public void saveCursos(List<Curso> cursos) {
        cursoRepo.saveAll(cursos);
    }

    @Override
    public Curso saveCurso(String nombreCurso) {
        if(cursoRepo.findCursoByNombre(nombreCurso).isEmpty()) return cursoRepo.save(new Curso(null, nombreCurso, null, null, null));
        else return null;
    }
}
