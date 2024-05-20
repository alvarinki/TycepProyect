package com.example.proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "foto")
    private String foto;

    @OneToMany(mappedBy = "idCurso")
    private Set<Alumno> alumnos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCurso")
    private Set<Horario> horarios = new LinkedHashSet<>();

//    @ManyToMany(mappedBy = "cursos")
//    @JsonIgnore
//    private Set<Profesor> profesores= new LinkedHashSet<>();

}