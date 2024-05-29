package com.example.pruebachats


data class Mensaje(
    val contenido: String,
    val fecha: String,
    val hora: String,
    val nombreUsuario: String
) {
    constructor() : this("", "", "", "")
}


