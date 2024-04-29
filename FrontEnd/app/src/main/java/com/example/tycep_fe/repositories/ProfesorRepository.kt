package com.example.tycep_fe.repositories

import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.services.ProfesorApi
import retrofit2.Response
import retrofit2.http.Path

class ProfesorRepository {

    suspend fun getAlumnosFromCurso(@Path("id") id:Int): Response<Set<Alumno>> = ProfesorApi.retrofitService.getAlumnosFromCurso(id)

    suspend fun getCursosFromProfesor(@Path("id") id:Int):Response<Set<Curso>> = ProfesorApi.retrofitService.getCursosFromProfesor(id)
}