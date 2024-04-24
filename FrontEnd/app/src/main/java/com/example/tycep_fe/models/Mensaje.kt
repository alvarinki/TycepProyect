package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Mensaje(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("idChat")
    val idChat:Int,
    @SerializedName("contenido")
    val contenido:String,
    @SerializedName("fecha")
    val fecha:String,
    @SerializedName("nombreUsuario")
    val nombreUsuario: String
)
