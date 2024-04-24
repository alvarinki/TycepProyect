package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Mensaje;
import com.example.proyecto.repositories.MensajeRepository;
import com.example.proyecto.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    MensajeRepository mensajeRepo;
    @Override
    public Mensaje saveMessage(Mensaje mensaje) {
        return mensajeRepo.save(mensaje);
    }
}
