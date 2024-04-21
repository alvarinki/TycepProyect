package com.example.recyclerrecorridos.preferences

import android.content.Context

class Prefs (val context: Context) {

    val SHARED_NAME= "recordarUsuario"
    val SHARED_TOKEN= "token"


    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveToken(token: String){
        storage.edit().putString(SHARED_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return storage.getString(SHARED_TOKEN, "")

    }

    fun clearToken(){
        if(storage.contains(SHARED_TOKEN)){
            storage.edit().remove(SHARED_TOKEN).apply()
        }
    }
}