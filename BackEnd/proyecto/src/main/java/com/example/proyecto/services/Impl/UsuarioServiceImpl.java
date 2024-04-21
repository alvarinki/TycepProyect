package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Usuario;
import com.example.proyecto.repositories.UsuarioRepository;
import com.example.proyecto.services.UsuarioService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    UsuarioRepository usuarioRepo;
    @Autowired
    JWTUtil jwtUtil;

    @Override
    public Usuario saveUsuario(Usuario user) {
        return usuarioRepo.save(user);
    }

    @Override
    public Optional<Usuario> findUsuarioByUsuario(String nombreUsuario) {
        return  usuarioRepo.findUsuarioByUsuario(nombreUsuario);
    }
}
