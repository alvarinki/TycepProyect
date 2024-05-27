package com.example.proyecto.repositories;

import com.example.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findUsuarioByUsuario(String nombreUsuario);

    Optional<Usuario> findUsuarioById(int idUsuario);

    Optional<Usuario> findUsuarioByNombreAndApellidos(String nombre, String apellidos);

    @Query("select dtype from Usuario where usuario =:nombreUsuario")
    Optional<Character> findDTypeFromUsuarioByUsuario(@Param("nombreUsuario") String nombreUsuario);

}
