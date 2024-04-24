package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Asignatura(
    @SerializedName("id")
    val id:Int,
    @SerializedName("nombre")
    val nombre:String
)
