package com.example.proyecto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tutor_legal_alumno")
public class TutorLegalAlumno {
    @EmbeddedId
    private TutorLegalAlumnoId id;

    @MapsId("idTutorLegal")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tutor_legal", nullable = false)
    private TutorLegal tutorLegal;

    @MapsId("idAlumno")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

}