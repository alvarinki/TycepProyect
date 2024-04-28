package com.example.proyecto.controllers;

import com.example.proyecto.model.Mensaje;
import com.example.proyecto.services.MensajeService;
import com.example.proyecto.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MensajeController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    MensajeService mensajeService;


    @PostMapping("/subir")
    public void subirMensaje(@RequestBody Mensaje mensaje, @RequestHeader String token){
        jwtUtil.validate(token);
        mensajeService.saveMessage(mensaje);
        //return new ResponseEntity<>(HttpStatus.OK);
    }
}
