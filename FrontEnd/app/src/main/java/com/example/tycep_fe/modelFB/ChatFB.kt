package com.example.tycep_fe.modelFB

data class ChatFB(
    var id: String = "", // Cambia el tipo de datos de Int a String
    val boletin: Boolean = false,
    val nombreChat: String = "",
    val mensajes: Map<String, MensajeFB> = emptyMap()
) {
    // Constructor vacío requerido por Firebase para la deserialización
    constructor() : this("", false, "", emptyMap())
}

