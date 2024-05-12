package com.example.proyecto.controllers;

import com.example.proyecto.FileManagers.FileManager;
import com.example.proyecto.dtos.*;
import com.example.proyecto.model.Profesor;
import com.example.proyecto.model.TutorLegal;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.services.AsignaturaService;
import com.example.proyecto.services.ProfesorService;
import com.example.proyecto.services.TutorService;
import com.example.proyecto.services.UsuarioService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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
    AsignaturaService asignaturaService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private FileManager fileManager;

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
        Optional<Usuario> user;

        if(loginRequest.getToken().isEmpty()){
            user= usuarioService.findUsuarioByUsuario(loginRequest.getUsername());
            System.out.println("Pasa por aquí");
            System.out.println(user);
        }
        else{
            user= usuarioService.findUsuarioByUsuario(jwtUtil.getValue(loginRequest.getToken()));
            System.out.println(jwtUtil.getValue(loginRequest.getToken()));
        }


        if(user.isPresent()){
            String encriptedPassword= user.get().getContrasena();
            if((encriptedPassword!=null && passwordEncoder.matches(loginRequest.getPassword(), encriptedPassword))||!loginRequest.getToken().isBlank()){
                String token=loginRequest.getToken();
                Usuario loggedUser= user.get();
                if(loginRequest.getToken().isBlank()){
                    token= jwtUtil.create(loggedUser.getId().toString(), loggedUser.getUsuario());
                }

                if(user.get().getDtype().toString().equals("T")){
                    TutorLegal tutorLegal= tutorService.findTutorLegalByUsuario_Id(loggedUser.getId());
                    return new ResponseEntity<>(new LoginResponseDto("Tutor Legal", tutorLegal, token), HttpStatus.OK);
                }
                else if(user.get().getDtype().toString().equals("P")){
                    Profesor profesor= profesorService.findProfesorByUsuario_Id(loggedUser.getId());
                    System.out.println(profesor + "\n" + token);
                    return new ResponseEntity<>(new LoginResponseDto("Profesor", profesor, token) , HttpStatus.OK);
                }
                else return new ResponseEntity<>(new LoginResponseDto("Admin", loggedUser, token), HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/admin/registerProfesores")
    public ResponseEntity<?> registerProfesores(@RequestBody String ruta){
        try {
            List<Profesor> profesores= fileManager.mapProfesores(ruta);
            return ResponseEntity.ok(profesores);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PostMapping("/admin/registerTutores")
    public ResponseEntity<?> registerTutores(@RequestBody String ruta){
        try {
            List<TutorLegal> tutores= fileManager.mapTutores(ruta);
            return ResponseEntity.ok(tutores);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @PostMapping("/admin/registerAdmins")
    public ResponseEntity<?> registerAdmins(@RequestBody String ruta){
        try {
            List<Usuario> admins= fileManager.mapAdmins(ruta);
            return ResponseEntity.ok(admins);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();

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



