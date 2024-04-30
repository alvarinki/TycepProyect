package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class TutorLegal(
    @SerializedName("id")
    val id: Int,
    @SerializedName("usuario")
    val usuario: String,
//    @SerializedName("contrasena")
//    val contrasena: contrasena,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("apellidos")
    val apellidos: String,
    @SerializedName("dtype")
    val dtype: Char,
    @SerializedName("chats")
    val chats: Set<Chat>?,
    @SerializedName("telefContacto")
    val telefContacto:String,
    @SerializedName("domicilio")
    val domicilio:String,
    @SerializedName("alumnos")
    var alumnos:Set<Alumno>?
)
