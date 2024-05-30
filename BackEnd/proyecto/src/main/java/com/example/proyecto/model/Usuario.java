package com.example.proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "dtype")
    private Character dtype;

//    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UsuarioChat> chats = new LinkedHashSet<>();



    public Map<String, Object> toMap() {
        Map<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("usuario", this.usuario);
        usuarioMap.put("contrasena", this.contrasena);
        usuarioMap.put("nombre", this.nombre);
        usuarioMap.put("apellidos", this.apellidos);
        usuarioMap.put("dtype", this.dtype);
        return usuarioMap;
    }
}