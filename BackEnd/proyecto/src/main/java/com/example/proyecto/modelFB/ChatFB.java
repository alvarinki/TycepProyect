package com.example.proyecto.modelFB;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatFB {
    private boolean boletin;
    private Map<String, MensajeFB> mensajes;
    private String nombreChat;
    private Map<String, Boolean> usuarios;

    public Map<String, Object> toMap() {
        Map<String, Object> chatMap = new HashMap<>();
        chatMap.put("boletin", boletin);
        chatMap.put("nombreChat", nombreChat);
        chatMap.put("mensajes", mensajes);
        chatMap.put("usuarios", usuarios);
        return chatMap;
    }
}
