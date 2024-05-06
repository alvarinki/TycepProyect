package com.example.recyclerrecorridos.preferences

import android.annotation.SuppressLint
import android.app.Application

class TokenUsuarioApplication: Application(){

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var prefs:Prefs
    }


    override fun onCreate() {
        super.onCreate()
        prefs= Prefs(applicationContext)
    }
}