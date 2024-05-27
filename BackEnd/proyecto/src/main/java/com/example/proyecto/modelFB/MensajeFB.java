package com.example.proyecto.modelFB;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MensajeFB {
    private String contenido;
    private String fecha;
    private String hora;
    private String nombreUsuario;

    public Map<String, Object> toMap() {
        Map<String, Object> mensajeMap = new HashMap<>();
        mensajeMap.put("contenido", contenido);
        mensajeMap.put("fecha", fecha);
        mensajeMap.put("hora", hora);
        mensajeMap.put("nombreUsuario", nombreUsuario);

        return mensajeMap;
    }
}
