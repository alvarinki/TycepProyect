package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName
import java.lang.AssertionError
import java.time.LocalDate

data class Falta(
    @SerializedName("id")
    val id:Int?,
    @SerializedName("hora")
    val hora:Int,
    @SerializedName("asignatura")
    val asignatura: String,
    @SerializedName("fecha")
    val fecha:String,
    @SerializedName("idAlumno")
    val idAlumno:Int,
    @SerializedName("justificada")
    val justificada:Boolean
)
