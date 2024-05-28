package com.example.proyecto.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminsUserData {
    String username;
    String password;
    String nombre_Apellidos;
    String dni;
}
