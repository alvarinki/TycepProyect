package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Alumno(
    @SerializedName("id")
    val id:Int,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("apellidos")
    val apellidos:String,
    @SerializedName("foto")
    val foto:String,
    @SerializedName("idCurso")
    val idCurso:Int,
    @SerializedName("dni")
    val dni:String,
    @SerializedName("faltas")
    var faltas:Set<Falta>?
)
