package com.example.tycep_fe.services

import com.example.tycep_fe.Dtos.FaltasCursoRequest
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.models.Falta
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

private const val urlBase = "http://alvarocf24.iesmontenaranco.com/"
//"http://192.168.56.1:8080/"
private val retrofit= Retrofit.Builder().baseUrl(urlBase).addConverterFactory(GsonConverterFactory.create()).build()

interface FaltaApiService{
    @POST("faltas/fromAlumno")
    suspend fun getFaltasFromAlumno(@Body idAlumno:Int, @Header(value = "token") token:String):Response<Set<Falta>>
    @POST("faltas/forAlumnos")
    suspend fun putFaltasForAlumnos(@Body faltas:List<Falta>, @Header(value="token") token: String)
    @POST("faltas/deleteFromAlumno")
    suspend fun deleteFaltaFromAlumno(@Body falta: Falta, @Header(value = "token") token: String)
    @POST("faltas/fromCurso")
    suspend fun getFaltasFromCurso(@Body faltasCursoRequest: FaltasCursoRequest, @Header(value = "token") token: String):Response<Set<Falta>>
}

object FaltaApi{
    val retrofitService: FaltaApiService by lazy { retrofit.create(FaltaApiService::class.java) }
}