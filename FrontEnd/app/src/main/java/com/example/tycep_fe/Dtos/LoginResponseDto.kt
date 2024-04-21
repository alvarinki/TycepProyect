package com.example.tycep_fe.Dtos

data class LoginResponseDto(
    val userType:String,
    val userData: Any,
    val token:String
)
