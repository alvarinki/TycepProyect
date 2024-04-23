package com.example.proyecto.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDto {
    private String userType;
    private Object userData;
    private String token;
}
