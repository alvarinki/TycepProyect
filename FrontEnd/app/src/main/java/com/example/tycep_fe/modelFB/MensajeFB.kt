package com.example.tycep_fe.modelFB


data class MensajeFB(
    val contenido: String,
    val fecha: String,
    val hora: String,
    val nombreUsuario: String
) {
    constructor() : this("", "", "", "")
}


