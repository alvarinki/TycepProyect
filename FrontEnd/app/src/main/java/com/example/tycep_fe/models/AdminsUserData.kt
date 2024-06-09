package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class AdminsUserData (
    @SerializedName("username")
    val username:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("nombre_Apellidos")
    val nombreApellidos:String,
    @SerializedName("dni")
    val dni:String
)