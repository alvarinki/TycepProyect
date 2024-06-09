package com.example.tycep_fe.Dtos

import com.example.tycep_fe.fragments.Alumnos
import com.example.tycep_fe.models.Alumno
import com.google.gson.annotations.SerializedName

data class AlumnoDto(
    @SerializedName("alumno")
    val alumno: Alumno,
    @SerializedName("tutores")
    val tutores:List<String>
)
