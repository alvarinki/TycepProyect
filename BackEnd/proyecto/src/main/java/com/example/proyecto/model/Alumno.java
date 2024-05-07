package com.example.proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "foto")
    private String foto;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id_curso", nullable = false)
//    private Curso idCurso;

    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "dni")
    private String dni;

    @OneToMany(mappedBy = "idAlumno")
    @JsonIgnore
    private Set<Falta> faltas = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private Set<TutorLegalAlumno> tutoresLegales = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "alumnos")
    @JsonIgnore
    private Set<TutorLegal> tutorLegales= new LinkedHashSet<>();

}