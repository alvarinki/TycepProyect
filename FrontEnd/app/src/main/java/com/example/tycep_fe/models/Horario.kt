package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Horario(
    @SerializedName("id")
    val id:Int,
    @SerializedName("asignatura")
    val asignatura:Asignatura,
    @SerializedName("dia")
    val dia:Dia,
    @SerializedName("hora")
    val hora:Int,
    @SerializedName("aula")
    val aula:Int,
    @SerializedName("id_profesor")
    val idProfesor: Int
)
