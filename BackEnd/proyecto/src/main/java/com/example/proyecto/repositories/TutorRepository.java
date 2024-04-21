package com.example.proyecto.repositories;

import com.example.proyecto.model.TutorLegal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<TutorLegal, Integer> {

    Optional<TutorLegal> findTutorLegalById(Integer userId);
}
