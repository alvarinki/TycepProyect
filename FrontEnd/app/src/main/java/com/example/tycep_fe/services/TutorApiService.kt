package com.example.tycep_fe.services

import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.Dtos.LoginResponseDto
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Horario
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.models.Usuario
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

private const val urlBase = "http://192.168.56.1:8080/"

//"http://alvarocf24.iesmontenaranco.com/"
private val retrofit= Retrofit.Builder().baseUrl(urlBase).addConverterFactory(GsonConverterFactory.create()).build()

interface TutorApiService{
    @POST("tutor/getAlumnos")
    suspend fun getTutorsAlumnos(@Body idTutor:Int, @Header(value = "token") token: String): Response<Set<Alumno>>
    @POST("tutor/getHorarioFromAlumno")
    suspend fun getHorarioFromAlumno(@Body idCurso:Int, @Header(value = "token") token: String):Response<Set<Horario>>
}

object TutorApi{
    val retrofitService: TutorApiService by lazy{ retrofit.create(TutorApiService::class.java)}
}