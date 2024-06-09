package com.example.tycep_fe.modelFB

data class ChatFB(
    var id: String? = null, // Cambia el tipo de datos de Int a String
    var boletin: Boolean = false,
    var nombreChat: String = "",
    var mensajes: Map<String, MensajeFB> = emptyMap(),
    var foto: String,
    var usuarios:Map<String, Boolean> = emptyMap()
) {
    // Constructor vacío requerido por Firebase para la deserialización
    constructor() : this("", false, "", emptyMap(), "", emptyMap())


}

