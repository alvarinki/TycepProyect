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
public class TutorLegalAlumnoId implements Serializable {
    private static final long serialVersionUID = 3711990131148726485L;
    @Column(name = "id_tutor_legal", nullable = false)
    private Integer idTutorLegal;

    @Column(name = "id_alumno", nullable = false)
    private Integer idAlumno;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TutorLegalAlumnoId entity = (TutorLegalAlumnoId) o;
        return Objects.equals(this.idAlumno, entity.idAlumno) &&
                Objects.equals(this.idTutorLegal, entity.idTutorLegal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlumno, idTutorLegal);
    }

}