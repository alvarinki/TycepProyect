package com.example.tycep_fe.models

import androidx.resourceinspection.annotation.Attribute.IntMap
import com.google.gson.annotations.SerializedName

data class Horario(
    @SerializedName("id")
    val id:Int,
    @SerializedName("asignatura")
    val asignatura:String,
    @SerializedName("dia")
    val dia:Dia,
    @SerializedName("hora")
    val hora:Int
)
