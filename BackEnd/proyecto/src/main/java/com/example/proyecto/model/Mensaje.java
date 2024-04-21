package com.example.proyecto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@Table(name = "mensaje")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_chat")
    private Integer idChat;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

}