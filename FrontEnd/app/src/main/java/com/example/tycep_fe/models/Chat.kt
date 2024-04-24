package com.example.tycep_fe.models

import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("id")
    val id:Int?,
    @SerializedName("nombreChat")
    val nombreChat:String,
    @SerializedName("boletin")
    val boletin:Boolean,
    @SerializedName("mensajes")
    val mensajes:Set<Mensaje>
)
