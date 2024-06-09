package com.example.proyecto.services.Impl;

import com.example.proyecto.dtos.TutorDto;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.repositories.TutorRepository;
import com.example.proyecto.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorServiceImpl implements TutorService {

    @Autowired
    TutorRepository tutorRepo;

    @Override
    public TutorLegal saveTutor(TutorLegal tutor) {
        return tutorRepo.save(tutor);
    }

    @Override
    public TutorLegal findTutorLegalByUsuario_Id(Integer userId) {
        Optional<TutorLegal> tutorLegal = tutorRepo.findById(userId);
        return tutorLegal.orElse(null);
    }

    @Override
    public void saveTutoresLegales(List<TutorLegal> tutoresLegales) {
        tutorRepo.saveAll(tutoresLegales);
    }

    @Override
    public void deleteTutores(List<TutorLegal> tutoresLegales) {
        tutorRepo.deleteAll(tutoresLegales);
    }

    @Override
    public int getIDTutorLegalByDni(String dni) {
        return tutorRepo.getIdByDni(dni).orElse(0);
    }

    @Override
    public TutorLegal findTutorLegalByDni(String dni) {
        return tutorRepo.findTutorLegalByDni(dni).orElse(null);
    }

    @Override
    public List<String> findTutoresByIdAlumno(Integer alumnoId) {
        return tutorRepo.findTutoresByIdAlumno(alumnoId);
    }

    @Override
    public void deleteTutorById(Integer id) {
        tutorRepo.deleteById(id);
    }
}
