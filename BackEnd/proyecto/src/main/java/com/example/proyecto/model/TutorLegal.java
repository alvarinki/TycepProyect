package com.example.proyecto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tutor_legal")
@PrimaryKeyJoinColumn(name = "usuario_id", referencedColumnName = "id")
public class TutorLegal extends Usuario{
//    @Id
//    @Column(name = "usuario_id", nullable = false)
//    private Integer id;
//
//    @MapsId
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "usuario_id", nullable = false)
//    private Usuario usuario;

    @Column(name = "telef_contacto", length = 20)
    private String telefContacto;

    @Column(name = "domicilio")
    private String domicilio;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="Tutor_legal_Alumno", joinColumns =
            {@JoinColumn(name = "id_tutor_legal")},
            inverseJoinColumns = {@JoinColumn(name = "id_alumno")})
    @JsonIgnore
    private Set<Alumno> alumnos= new LinkedHashSet<>();

//    @OneToMany(mappedBy = "tutorLegal", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<TutorLegalAlumno> hijos = new LinkedHashSet<>();
}