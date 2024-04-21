package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Curso(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("alumnos")
    val alumnos: Set<Alumno>,
    @SerializedName("horarios")
    val horario:Set<Horario>
)
