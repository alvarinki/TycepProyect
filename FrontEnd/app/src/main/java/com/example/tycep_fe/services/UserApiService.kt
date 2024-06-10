package com.example.tycep_fe.services

import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.Dtos.LoginResponseDto
import com.example.tycep_fe.Dtos.PhotoRequest
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
import retrofit2.http.Url

private const val urlBase = "http://192.168.56.1:8080/"

private val retrofit= Retrofit.Builder().baseUrl(urlBase).addConverterFactory(GsonConverterFactory.create()).build()

interface UserApiService{
    @POST("user/login")
    suspend fun loginUser(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>
    @POST("user/postPhoto")
    suspend fun postPhoto(@Body request: PhotoRequest, @Header(value="token") token: String )
}

object UserApi{
    val retrofitService: UserApiService by lazy{ retrofit.create(UserApiService::class.java)}
}