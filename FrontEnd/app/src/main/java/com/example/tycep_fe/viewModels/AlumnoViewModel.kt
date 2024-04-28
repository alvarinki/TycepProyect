package com.example.tycep_fe.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.repositories.AlumnoRepository
import com.example.tycep_fe.repositories.FaltaRepository
import kotlinx.coroutines.launch

class AlumnoViewModel: ViewModel() {

    private val alumnoRepo= AlumnoRepository()
    private val faltasRepo= FaltaRepository()

    var _alumno= MutableLiveData<Alumno>()

    fun getAlumnoById(idAlumno: Int, token:String){
        viewModelScope.launch {
           val response = alumnoRepo.getAlumnoById(idAlumno, token)
            if(response.isSuccessful){
                val alumno:Alumno= response.body()!!
                _alumno.postValue(alumno)
            }
        }
    }
}