package com.example.pruebapantallas

data class Falta(
    val id:Int?,
    val hora:Int,
    val asignatura:String,
    val fecha:String,
    val idAlumno:Int,
    val justificada:Boolean
)
