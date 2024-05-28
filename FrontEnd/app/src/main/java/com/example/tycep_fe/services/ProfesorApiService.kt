package com.example.tycep_fe.services

import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.models.Dia
import com.example.tycep_fe.models.Horario
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

private const val urlBase = "http://192.168.56.1:8080/"

private val retrofit= Retrofit.Builder().baseUrl(urlBase).addConverterFactory(GsonConverterFactory.create()).build()

interface ProfesorApiService{
    @GET("profesor/cursos/{id}")
    suspend fun getCursosFromProfesor(@Path("id") id:Int):Response<Set<Curso>>
    @GET("profesor/cursos/alumnos/{id}")
    suspend fun getAlumnosFromCurso(@Path("id") id:Int):Response<Set<Alumno>>
    @GET("profesor/horarioFaltas/{idProfesor}/{dia}")
    suspend fun getHorariosForFaltas(@Path("idProfesor") idProfesor: Int, @Path("dia") dia: Dia):Response<Set<Horario>>
    @GET("profesor/horario/{idProfesor}")
    suspend fun getHorarioFromProfesor(@Path("idProfesor") idProfesor: Int): Response<Set<Horario>>
}

object ProfesorApi{
    val retrofitService: ProfesorApiService by lazy { retrofit.create(ProfesorApiService::class.java) }
}