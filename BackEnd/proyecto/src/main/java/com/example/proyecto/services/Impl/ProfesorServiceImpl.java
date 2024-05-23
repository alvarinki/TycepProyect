package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Curso;
import com.example.proyecto.model.Profesor;
import com.example.proyecto.repositories.CursoRepository;
import com.example.proyecto.repositories.ProfesorRepository;
import com.example.proyecto.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    ProfesorRepository profesorRepo;

    @Autowired
    CursoRepository cursoRepo;


    @Override
    public Profesor findProfesorByUsuario_Id(Integer userId) {
        return profesorRepo.findProfesorById(userId).orElse(null);
    }

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        return profesorRepo.save(profesor);
    }

    @Override
    public Set<Curso> getCursosFromProfesor(int id) {
        Optional<Profesor> profe= profesorRepo.findProfesorById(id);
        return profe.map(Profesor::getCursos).orElse(null);
    }

    @Override
    public int findProfesorByDni(String dni) {
        Optional<Profesor> profesor= profesorRepo.findProfesorByDni(dni);
        if(profesor.isPresent()) return profesor.get().getId();
        else return 0;
    }

    @Override
    public void saveProfesores(List<Profesor> profesores) {
        profesorRepo.saveAll(profesores);
    }

    @Override
    public void deleteProfesores(List<Profesor> profesores) {
        profesorRepo.deleteAll(profesores);
    }
}