package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id:Int,
    @SerializedName("usuario")
    val usuario:String,
    @SerializedName("contrasena")
    val contrasena:String,
    @SerializedName("nombre")
    val nombre:String,
    @SerializedName("apellidos")
    val apellidos:String,
    @SerializedName("dtype")
    val dtype:Char
//    @SerializedName("chats")
//    val chats: Set<Chat>
)
