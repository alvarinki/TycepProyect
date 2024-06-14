package com.example.tycep_fe.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tycep_fe.Dtos.AlumnoDto
import com.example.tycep_fe.Dtos.FaltasCursoRequest
import com.example.tycep_fe.Dtos.TutorDto
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.repositories.AlumnoRepository
import com.example.tycep_fe.repositories.FaltaRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AlumnoViewModel : ViewModel() {

    private val alumnoRepo = AlumnoRepository()
    private val faltasRepo = FaltaRepository()


    var _alumno = MutableLiveData<Alumno>()
    var _faltas = MutableLiveData<Set<Falta>>()
     var _tutores=MutableLiveData<List<String>>()
    fun getAlumnoById(idAlumno: Int, token: String) {
        viewModelScope.launch {
            val response = alumnoRepo.getAlumnoById(idAlumno, token)
            if (response.isSuccessful) {
                val alumnodto: AlumnoDto = response.body()!!
                _alumno.postValue(alumnodto.alumno)
                println(alumnodto.tutores.get(0).toString())
                val tutoresFormateados= alumnodto.tutores.map { t -> t.split("-")[0] +" "+ t.split("-")[1] }
                _tutores.postValue(tutoresFormateados)
            }
        }
    }

    fun putFaltas(faltas: List<Falta>, token: String) {
        viewModelScope.launch {
            faltasRepo.putFaltasForAlumnos(faltas, token)
        }
    }

    fun getFaltasFromAlumno(token: String) {
        viewModelScope.launch {
            val response = faltasRepo.getFaltasFromAlumno(_alumno.value?.id!!, token)
            if (response.isSuccessful) {
                val faltasAnadidas = response.body()
                _alumno.postValue(_alumno.value.apply { this?.faltas = faltasAnadidas })
            }
        }
    }

    fun deleteFaltaFromAlumno(falta: Falta, token: String) {
        viewModelScope.launch {
            faltasRepo.deleteFaltaFromAlumno(falta, token)
        }
    }

    fun getFaltasFromCurso(faltasCursoRequest: FaltasCursoRequest, token: String) {
        viewModelScope.launch {
            val response = faltasRepo.getFaltasFromCurso(faltasCursoRequest, token)
            if (response.isSuccessful) {
                val faltas = response.body()
                println(faltas)
                _faltas.postValue(faltas!!)
            }
        }
    }

    fun filtrarJustificadas() {
        val faltasFiltradas = _faltas.value?.filter { it.justificada }?.toSet() ?: emptySet()
        _faltas.value = faltasFiltradas
    }

    fun filtrarInjustificadas() {
        val faltasFiltradas = _faltas.value?.filter { !it.justificada }?.toSet() ?: emptySet()
        _faltas.value = faltasFiltradas
    }

    fun filtrarPorAsignatura(asignatura: String) {
        val faltasFiltradas = _faltas.value?.filter { it.asignatura == asignatura }?.toSet() ?: emptySet()
        _faltas.value = faltasFiltradas
    }

    fun filtrarEntreFechas(fechaInicio: Date, fechaFin: Date) {
        val faltasFiltradas = _faltas.value?.filter {
            val fecha = SimpleDateFormat("yyyy-MM-dd").parse(it.fecha)
            fecha.after(fechaInicio) && fecha.before(fechaFin)
        }?.toSet() ?: emptySet()
        _faltas.value = faltasFiltradas
    }

    // Restaura la lista original de _faltas
    fun restaurarFaltas(originalFaltas: Set<Falta>) {
        _faltas.value = originalFaltas
    }
//    fun getTutoresFromAlumno(idAlumno: Int){
//        viewModelScope.launch {
//            val response = alumnoRepo.getTutoresFromAlumno(idAlumno)
//            println("No va")
//            if(response.isSuccessful){
//                val tutors:List<TutorDto> = response.body()!!
//                _tutores.postValue(tutors)
//            }
//        }
//    }
}