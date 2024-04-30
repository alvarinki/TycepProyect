package com.example.tycep_fe.repositories

import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.services.AlumnoApi
import com.example.tycep_fe.services.TutorApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

class TutorRepository {
    suspend fun getTutorsAlumnos(@Body idTutor:Int, @Header(value = "token") token: String): Response<Set<Alumno>> = TutorApi.retrofitService.getTutorsAlumnos(idTutor, token)

}