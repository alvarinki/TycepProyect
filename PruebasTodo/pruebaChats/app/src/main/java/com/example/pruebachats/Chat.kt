package com.example.pruebachats

data class Chat(
    var id: String = "", // Cambia el tipo de datos de Int a String
    val boletin: Boolean = false,
    val nombreChat: String = "",
    val mensajes: Map<String, Mensaje> = emptyMap()
) {
    // Constructor vacío requerido por Firebase para la deserialización
    constructor() : this("", false, "", emptyMap())
}

