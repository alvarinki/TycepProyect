package com.example.proyecto.controllers;

import com.example.proyecto.dtos.*;
import com.example.proyecto.model.Profesor;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.model.TutorLegalAlumno;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.services.Impl.TutorServiceImpl;
import com.example.proyecto.services.ProfesorService;
import com.example.proyecto.services.TutorService;
import com.example.proyecto.services.UsuarioService;
import com.example.proyecto.util.JWTUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @GetMapping
    public String prueba(){
        return "Hola";
    }

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TutorService tutorService;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/register/tutor")
    public ResponseEntity<TutorLegal> tutorRegistration(@RequestBody TutorLegal tutor){
        if(tutor!=null){
            if(tutor.getDtype().toString().equals("T")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                tutor.setContrasena(passwordEncoder.encode(tutor.getContrasena()));
                TutorLegal tutorLegal = tutorService.saveTutor(tutor);
                return new ResponseEntity<>(tutorLegal, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register/profesor")
    public ResponseEntity<Profesor> teacherRegistration(@RequestBody Profesor profe){
        if(profe!=null){
            if(profe.getDtype().toString().equals("P")) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                profe.setContrasena(passwordEncoder.encode(profe.getContrasena()));
                Profesor profesor = profesorService.saveProfesor(profe);
                return new ResponseEntity<>(profesor, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Usuario> userRegistration(@RequestBody Usuario user){

        if(user!=null){
        if(user.getDtype().toString().equals("A")){
            return new ResponseEntity<>(usuarioService.saveUsuario(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequest loginRequest){
        PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
        Optional<Usuario> user= usuarioService.findUsuarioByUsuario(loginRequest.getUsername());
        if(user.isPresent()){
            String encriptedPassword= user.get().getContrasena();
            if(encriptedPassword!=null && passwordEncoder.matches(loginRequest.getPassword(), encriptedPassword)){
                Usuario loggedUser= user.get();
                String token= jwtUtil.create(loggedUser.getId().toString(), loggedUser.getUsuario());
                if(user.get().getDtype().toString().equals("T")){
                    TutorLegal tutorLegal= tutorService.findTutorLegalByUsuario_Id(loggedUser.getId()).get();
                    return new ResponseEntity<>(new LoginResponseDto("Tutor Legal", tutorLegal, token), HttpStatus.OK);
                }
                else if(user.get().getDtype().toString().equals("P")){
                    Profesor profesor= profesorService.findProfesorByUsuario_Id(loggedUser.getId());
                    System.out.println(profesor);
                    return new ResponseEntity<>(new LoginResponseDto("Profesor", profesor, token) , HttpStatus.OK);
                }
                else return new ResponseEntity<>(new LoginResponseDto("Admin", loggedUser, token), HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
