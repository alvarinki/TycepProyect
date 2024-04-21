package com.example.proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombrechat")
    private String nombreChat;

    @Column(name = "boletin")
    private Boolean boletin;

    @OneToMany(mappedBy = "idChat")
    private Set<Mensaje> mensajes = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private Set<UsuarioChat> usuarios = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "chats")
    @JsonIgnore
    private Set<Usuario> usuarios= new LinkedHashSet<>();


}