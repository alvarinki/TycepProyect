package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.repositories.AlumnoRepository;
import com.example.proyecto.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepo;

    @Override
    public Set<Alumno> findAlumnosByIdCurso(int idCurso) {
        Set<Alumno> alumnos = alumnoRepo.getAlumnosByIdCurso(idCurso);
        if (alumnos != null) return alumnoRepo.getAlumnosByIdCurso(idCurso);
        else return null;
    }

    @Override
    public Alumno findAlumnoById(int id) {
        Optional<Alumno> alumno = alumnoRepo.findAlumnoById(id);
        return alumno.orElse(null);
    }

    @Override
    public Optional<Alumno> findAlumnoByNombre(String nombre) {
        return alumnoRepo.findAlumnoByNombre(nombre);
    }

    @Override
    public Alumno findAlumnoByDni(String dni) {
        return alumnoRepo.findAlumnoByDni(dni).orElse(null);
    }

    @Override
    public int getIdAlumnoByDni(String dni) {
        return alumnoRepo.getIdAlumnoByDni(dni).orElse(0);
    }

    @Override
    public void saveAlumnos(List<Alumno> alumnos) {
        alumnoRepo.saveAll(alumnos);
    }

    @Override
    public void deleteAlumnos(List<Alumno> alumnos) {
        alumnoRepo.deleteAll(alumnos);
    }

    @Override
    public void saveFotoFromAlumno(int idAlumno, String foto) {
        Optional<Alumno> alumno = alumnoRepo.findAlumnoById(idAlumno);
        if(alumno.isPresent()) {
            alumno.get().setFoto(foto);
            alumnoRepo.save(alumno.get());
        }
    }

    @Override
    public void deleteAlumnoById(int idAlumno) {
        alumnoRepo.deleteById(idAlumno);
    }

}
