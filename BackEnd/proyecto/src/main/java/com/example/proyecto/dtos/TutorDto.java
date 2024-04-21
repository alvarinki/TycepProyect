package com.example.proyecto.dtos;

import com.example.proyecto.model.TutorLegal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TutorDto {
    TutorLegal tutor;
    String token;
}
