package com.example.tycep_fe.repositories

import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.services.FaltaApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

class FaltaRepository {
    suspend fun getFaltasFromAlumno(idAlumno:Int, token:String): Response<Set<Falta>> = FaltaApi.retrofitService.getFaltasFromAlumno(idAlumno, token)
    suspend fun putFaltasForAlumnos(@Body faltas:List<Falta>, @Header(value="token") token: String)= FaltaApi.retrofitService.putFaltasForAlumnos(faltas, token)
}