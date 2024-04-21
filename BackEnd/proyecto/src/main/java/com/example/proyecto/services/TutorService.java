package com.example.proyecto.services;

import com.example.proyecto.dtos.TutorDto;
import com.example.proyecto.model.TutorLegal;

import java.util.Optional;

public interface TutorService {

    TutorLegal saveTutor(TutorLegal tutor);

    Optional<TutorLegal> findTutorLegalByUsuario_Id(Integer userId);
}
