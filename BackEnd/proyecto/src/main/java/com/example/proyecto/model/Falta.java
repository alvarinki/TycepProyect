package com.example.proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faltas")
public class Falta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "hora", nullable = false)
    private Integer hora;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "justificada")
    private boolean justificada;

    @Column(name = "id_Alumno")
    private int idAlumno;

}