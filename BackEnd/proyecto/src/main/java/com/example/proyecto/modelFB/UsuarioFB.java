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
        private String username;
        private Map<String, Boolean> chats;

        public Map<String, Object> toMap() {
                Map<String, Object> result = new HashMap<>();
                result.put("username", username);
                result.put("chats", chats);
                return result;
        }
}
