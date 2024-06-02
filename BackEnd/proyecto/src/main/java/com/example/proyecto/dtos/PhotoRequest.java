package com.example.proyecto.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PhotoRequest {
    private String photo;
    private int requiredId;
    private String type;
}
