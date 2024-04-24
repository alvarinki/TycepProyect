package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName


data class Profesor(
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
    @SerializedName("dni")
    val dni:String,
    @SerializedName("correo")
    val correo:String,
    @SerializedName("cursos")
    val cursos:Set<Curso>?,
    @SerializedName("asignaturas")
    val asignaturas:Set<Asignatura>
)
