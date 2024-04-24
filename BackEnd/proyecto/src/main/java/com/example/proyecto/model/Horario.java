package com.example.proyecto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "horario")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "hora", nullable = false)
    private Integer hora;

    @Column(name = "id_curso")
    private Integer idCurso;

    @ManyToOne()
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

}