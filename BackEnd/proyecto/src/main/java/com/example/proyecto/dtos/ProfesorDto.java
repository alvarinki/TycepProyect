package com.example.proyecto.dtos;

import com.example.proyecto.model.Profesor;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProfesorDto {
    Profesor profesor;
    String token;
}
