package com.example.proyecto.dtos;

import com.example.proyecto.model.Usuario;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminDto {
    Usuario user;
    String token;
}
