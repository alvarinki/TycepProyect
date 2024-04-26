package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Falta;
import com.example.proyecto.repositories.FaltaRepository;
import com.example.proyecto.services.FaltaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FaltaServiceImpl implements FaltaService {

    @Autowired
    private FaltaRepository faltaRepo;

    @Override
    public Set<Falta> findFaltasByIdAlumno(int alumnoId) {
        return faltaRepo.findFaltasByIdAlumno_Id(alumnoId);
    }

    @Override
    public List<Falta> saveFaltas(List<Falta> faltas) {
        return faltaRepo.saveAll(faltas);
    }

    @Override
    public Falta updateFalta(Falta falta) {
        return faltaRepo.save(falta);
    }

    @Override
    public void deleteFalta(Falta falta) {
         faltaRepo.delete(falta);
    }

    @Override
    public Falta findFaltaById(int id) {
         return faltaRepo.findFaltaById(id);
    }

}
