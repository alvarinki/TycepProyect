package com.example.proyecto.services;

import com.example.proyecto.dtos.TutorDto;
import com.example.proyecto.model.TutorLegal;

import java.util.List;
import java.util.Optional;

public interface TutorService {

    TutorLegal saveTutor(TutorLegal tutor);

    TutorLegal findTutorLegalByUsuario_Id(Integer userId);

    void saveTutoresLegales(List<TutorLegal> tutoresLegales);

    void deleteTutores(List<TutorLegal> tutoresLegales);

    int findTutorLegalByDni(String dni);
}
