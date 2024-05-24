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
        return new HashSet<>(faltaRepo.findFaltasByIdAlumno(alumnoId));
    }

    @Override
    public void saveFaltas(List<Falta> faltas) {
        for (Falta falta : faltas) {
            Falta f = faltaRepo.findFaltaByHoraAndIdAlumnoAndFecha(falta.getHora(), falta.getIdAlumno(), falta.getFecha());
            if (f != null) {
                falta.setId(f.getId());
                faltaRepo.save(falta);
            } else {
                faltaRepo.save(falta);
            }
        }
    }

    @Override
    public Falta updateFalta(Falta falta) {
        return faltaRepo.save(falta);
    }

    @Override
    public Falta saveFalta(Falta falta) {
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
