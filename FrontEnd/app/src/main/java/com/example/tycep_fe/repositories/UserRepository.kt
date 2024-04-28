package com.example.tycep_fe.repositories

import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.Dtos.LoginResponseDto
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.services.UserApi
import retrofit2.Response
import retrofit2.http.Body

class UserRepository {

    suspend fun userLogin(loginRequestDto: LoginRequestDto):Response<LoginResponseDto> = UserApi.retrofitService.loginUser(loginRequestDto)

    suspend fun userRecovery(token:String, userType:String):Response<Any> = UserApi.retrofitService.userRecovery(token, userType)

    suspend fun uploadMessage(message: Mensaje, token:String) = UserApi.retrofitService.uploadMessage(message, token)
}