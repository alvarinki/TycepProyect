package com.example.proyecto.dtos;

import com.example.proyecto.model.Alumno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlumnoDto {
    private Alumno alumno;
    private List<String> tutores;
}
