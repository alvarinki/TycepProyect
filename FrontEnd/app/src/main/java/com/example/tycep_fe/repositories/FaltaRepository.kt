package com.example.tycep_fe.repositories

import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.services.FaltaApi
import retrofit2.Response
import retrofit2.http.Body

class FaltaRepository {
    suspend fun getFaltasFromAlumno(@Body idAlumno:Int): Response<Set<Falta>> = FaltaApi.retrofitService.getFaltasFromAlumno(idAlumno)
}