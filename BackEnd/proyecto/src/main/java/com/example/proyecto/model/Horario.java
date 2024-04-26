package com.example.proyecto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "horario")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "hora", nullable = false)
    private Integer hora;

    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "aula")
    private Integer aula;

    @Column(name = "id_profesor")
    private Integer idProfesor;
}