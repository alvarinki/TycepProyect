package com.example.proyecto.services.Impl;

import com.example.proyecto.model.Alumno;
import com.example.proyecto.model.Falta;
import com.example.proyecto.repositories.AlumnoRepository;
import com.example.proyecto.repositories.FaltaRepository;
import com.example.proyecto.repositories.UsuarioRepository;
import com.example.proyecto.services.FaltaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FaltaServiceImpl implements FaltaService {

    @Autowired
    private FaltaRepository faltaRepo;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public Set<Falta> findFaltasByIdAlumno(int alumnoId) {
        return new HashSet<>(faltaRepo.findFaltasByIdAlumno(alumnoId));
    }

    @Override
    public void saveFaltas(List<Falta> faltas) {
        for (Falta falta : faltas) {
            Falta f = faltaRepo.findFaltaByHoraAndIdAlumnoAndFecha(falta.getHora(), falta.getIdAlumno(), falta.getFecha());
            if (f != null) {
                falta.setId(f.getId());
                faltaRepo.save(falta);
            } else {
                faltaRepo.save(falta);
            }
        }
    }

    @Override
    public Falta updateFalta(Falta falta) {
        return faltaRepo.save(falta);
    }

    @Override
    public Falta saveFalta(Falta falta) {
        return faltaRepo.save(falta);
    }

    @Override
    public void deleteFalta(Falta falta) {
        faltaRepo.delete(falta);
    }

    @Override
    public Falta findFaltaById(int id) {
        return faltaRepo.findFaltaById(id);
    }

    @Override
    public Set<Falta> findFaltasByIdCurso(int idCurso) {
        Set<Falta> faltas= faltaRepo.findFaltasByIdCurso(idCurso).orElse(null);
        if(faltas!=null){
           for(Falta f:faltas){
               Alumno alumno= alumnoRepository.findAlumnoById(f.getIdAlumno()).orElse(null);
               if(alumno!=null){
                   f.setAsignatura(alumno.getNombre()+" "+alumno.getApellidos());
               }
           }
           return faltas;
        }
        else return null;
    }

    @Override
    public List<Falta> findFaltasByIdAlumnoAndAsignatura(Integer idAlumno, String asignatura) {
        List<Falta> faltas=faltaRepo.findFaltasByIdAlumnoAndAsignatura(idAlumno,asignatura).orElse(null);
        return adaptarParaMostrarAlumnos(faltas);
    }

    private List<Falta> adaptarParaMostrarAlumnos(List<Falta> faltas) {
        if(faltas !=null){
            for(Falta f: faltas){
                Alumno alumno= alumnoRepository.findAlumnoById(f.getIdAlumno()).orElse(null);
                if(alumno!=null){
                    f.setAsignatura(alumno.getNombre()+" "+alumno.getApellidos());
                }
            }
            return faltas;
        }
        else return null;
    }

}
