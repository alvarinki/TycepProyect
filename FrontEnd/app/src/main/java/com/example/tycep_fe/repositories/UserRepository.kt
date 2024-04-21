package com.example.tycep_fe.repositories

import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.Dtos.LoginResponseDto
import com.example.tycep_fe.services.UserApi
import retrofit2.Response

class UserRepository {

    suspend fun userLogin(loginRequestDto: LoginRequestDto):Response<LoginResponseDto> = UserApi.retrofitService.loginUser(loginRequestDto)

}