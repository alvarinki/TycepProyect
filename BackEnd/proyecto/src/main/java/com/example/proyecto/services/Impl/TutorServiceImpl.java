package com.example.proyecto.services.Impl;

import com.example.proyecto.dtos.TutorDto;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.repositories.TutorRepository;
import com.example.proyecto.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<TutorLegal> findTutorLegalByUsuario_Id(Integer userId) {
        return tutorRepo.findTutorLegalById(userId);
    }

}
