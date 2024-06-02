package com.example.proyecto.controllers;

import com.example.proyecto.FileManagers.FileManager;
import com.example.proyecto.dtos.*;
import com.example.proyecto.model.Profesor;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.services.*;
import com.example.proyecto.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TutorService tutorService;

    @Autowired
    ProfesorService profesorService;

    @Autowired
    AsignaturaService asignaturaService;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private FileManager fileManager;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequest loginRequest) {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        Optional<Usuario> user;

        if (loginRequest.getToken().isEmpty()) {
            user = usuarioService.findUsuarioByUsuario(loginRequest.getUsername());
        } else {
            user = usuarioService.findUsuarioByUsuario(jwtUtil.getValue(loginRequest.getToken()));
        }


        if (user.isPresent()) {
            String encriptedPassword = user.get().getContrasena();
            if ((encriptedPassword != null && passwordEncoder.matches(loginRequest.getPassword(), encriptedPassword)) || !loginRequest.getToken().isBlank()) {
                String token = loginRequest.getToken();
                Usuario loggedUser = user.get();
                if (loginRequest.getToken().isBlank()) {
                    token = jwtUtil.create(loggedUser.getId().toString(), loggedUser.getUsuario());
                }

                if (user.get().getDtype().toString().equals("T")) {
                    TutorLegal tutorLegal = tutorService.findTutorLegalByUsuario_Id(loggedUser.getId());
                    return new ResponseEntity<>(new LoginResponseDto("Tutor Legal", tutorLegal, token), HttpStatus.OK);
                } else if (user.get().getDtype().toString().equals("P")) {
                    Profesor profesor = profesorService.findProfesorByUsuario_Id(loggedUser.getId());
                    return new ResponseEntity<>(new LoginResponseDto("Profesor", profesor, token), HttpStatus.OK);
                } else return new ResponseEntity<>(new LoginResponseDto("Admin", loggedUser, token), HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/postPhoto")
    public void postPhoto(@RequestBody PhotoRequest photoRequest, @RequestHeader String token)  {
        jwtUtil.validate(token);
        System.out.println(photoRequest.getType());
        System.out.println(photoRequest.getPhoto());
        System.out.println(photoRequest.getRequiredId());
        switch (photoRequest.getType()) {
            case "Student"-> alumnoService.saveFotoFromAlumno(photoRequest.getRequiredId(), photoRequest.getPhoto());
            case "Usuario"->{}

        }
    }

}


//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequest loginRequest){
//        PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        Optional<Usuario> user= usuarioService.findUsuarioByUsuario(loginRequest.getUsername());
//        if(user.isPresent()){
//            //String encriptedPassword= user.get().getContrasena();
//            //if(encriptedPassword!=null && passwordEncoder.matches(loginRequest.getPassword(), encriptedPassword)){
//                Usuario loggedUser= user.get();
//                String token= jwtUtil.create(loggedUser.getUsuario(), loggedUser.getId().toString());
//                if(user.get().getDtype().toString().equals("T")){
//                    return new ResponseEntity<>(new LoginResponseDto("Tutor Legal", token), HttpStatus.OK);
//                }
//                else if(user.get().getDtype().toString().equals("P")){
//                    return new ResponseEntity<>(new LoginResponseDto("Profesor", token) , HttpStatus.OK);
//                }
//                else return new ResponseEntity<>(new LoginResponseDto("Admin", token), HttpStatus.OK);
//            //}
//            //else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping("/recovery")
//    public ResponseEntity<?> userRecovery(@RequestHeader("Authorization") String token, @RequestBody String userType) {
//        jwtUtil.validate(token);
//
//        if(userType.equalsIgnoreCase("Profesor")){
//        System.out.println(userType);
//        int idUsuario = Integer.parseInt(jwtUtil.getValue(token));
//        System.out.println(idUsuario);
//        Profesor profesor = profesorService.findProfesorByUsuario_Id(idUsuario);
//        return ResponseEntity.ok().body(profesor);
//        }
//        else if(userType.equalsIgnoreCase("Tutor Legal")){
//            int idUsuario = Integer.parseInt(jwtUtil.getValue(token));
//            System.out.println(idUsuario);
//            TutorLegal tutorLegal= tutorService.findTutorLegalByUsuario_Id(idUsuario);
//            return ResponseEntity.ok().body(tutorLegal);
//        }
//        else if(userType.equalsIgnoreCase("Admin")){
//            int idUsuario = Integer.parseInt(jwtUtil.getValue(token));
//            Usuario user= usuarioService.findUsuarioById(idUsuario);
//            return ResponseEntity.ok().body(user);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }



