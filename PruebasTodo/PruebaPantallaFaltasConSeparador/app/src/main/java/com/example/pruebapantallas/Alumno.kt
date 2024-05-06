package com.example.pruebapantallas



data class Alumno(
    val id:Int,
    val nombre:String,
    val apellidos:String,
    val foto:String,
    val idCurso:Int,
    var faltas:Set<Falta>?
)
