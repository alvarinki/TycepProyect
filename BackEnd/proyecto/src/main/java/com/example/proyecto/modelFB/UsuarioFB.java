package com.example.proyecto.modelFB;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioFB {
        private String id;
        private String apellidos;
        private String nombre;
        private String username;
        private Map<String, Boolean> chats;

        public Map<String, Object> toMap() {
                Map<String, Object> result = new HashMap<>();
                result.put("id", id); // Incluir id en el mapa
                result.put("apellidos", apellidos);
                result.put("nombre", nombre);
                result.put("username", username);
                result.put("chats", chats);
                return result;
        }
}
