package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Falta(
    @SerializedName("id")
    val id:Int,
    @SerializedName("hora")
    val hora:Int,
    @SerializedName("fecha")
    val fecha:String,
    @SerializedName("justificada")
    val justificada:Boolean
)
