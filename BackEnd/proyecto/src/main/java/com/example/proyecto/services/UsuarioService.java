package com.example.proyecto.services;

import com.example.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UsuarioService {

    Usuario saveUsuario(Usuario user);

    Optional<Usuario> findUsuarioByUsuario(String nombreUsuario);

    Usuario findUsuarioById(int id);

    void saveUsers(List<Usuario> users);

    void deleteUsuarios(List<Usuario> users);

    Usuario findUsuarioByNombreAndApellidos(String nombre, String apellidos);


    Character findDTypeFromUsuarioByUsuario( String nombreUsuario);
}
