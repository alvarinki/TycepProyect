package com.example.proyecto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProfesorCursoId implements Serializable {
    private static final long serialVersionUID = 4982235872604388450L;
    @Column(name = "id_profesor", nullable = false)
    private Integer idProfesor;

    @Column(name = "id_curso", nullable = false)
    private Integer idCurso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProfesorCursoId entity = (ProfesorCursoId) o;
        return Objects.equals(this.idProfesor, entity.idProfesor) &&
                Objects.equals(this.idCurso, entity.idCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProfesor, idCurso);
    }

}