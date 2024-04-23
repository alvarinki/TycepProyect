package com.example.proyecto.services;

import com.example.proyecto.model.Usuario;

import java.util.Optional;


public interface UsuarioService {

    Usuario saveUsuario(Usuario user);

    Optional<Usuario> findUsuarioByUsuario(String nombreUsuario);

    Usuario findUsuarioById(int id);
}
