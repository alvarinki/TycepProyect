package com.example.proyecto.repositories;

import com.example.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findUsuarioByUsuario(String nombreUsuario);

    Optional<Usuario> findUsuarioById(int idUsuario);
}
