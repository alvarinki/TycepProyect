package com.example.tycep_fe.services

import com.example.tycep_fe.Dtos.AlumnoDto
import com.example.tycep_fe.Dtos.TutorDto
import com.example.tycep_fe.models.Alumno

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private const val urlBase = "http://alvarocf24.iesmontenaranco.com/"
//"http://192.168.56.1:8080/"
private val retrofit= Retrofit.Builder().baseUrl(urlBase).addConverterFactory(GsonConverterFactory.create()).build()

interface AlumnoApiService{
    @POST("alumno/getAlumno")
    suspend fun getAlumnoById(@Body idAlumno:Int, @Header(value="token") token:String): Response<AlumnoDto>

    @POST("/getTutoresFromAlumno")
    suspend fun getTutoresFromAlumno(@Body idAlumno: Int): Response<List<TutorDto>>
 }

object AlumnoApi{
    val retrofitService: AlumnoApiService by lazy { retrofit.create(AlumnoApiService::class.java) }
}