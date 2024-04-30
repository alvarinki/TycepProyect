package com.example.tycep_fe.repositories

import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.services.AlumnoApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

class AlumnoRepository {
    suspend fun getAlumnoById(idAlumno:Int, token:String): Response<Alumno> = AlumnoApi.retrofitService.getAlumnoById(idAlumno, token)

}
