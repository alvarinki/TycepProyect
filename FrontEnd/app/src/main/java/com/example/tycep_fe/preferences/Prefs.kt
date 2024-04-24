package com.example.recyclerrecorridos.preferences

import android.content.Context

class Prefs (val context: Context) {

    val SHARED_NAME= "recordarUsuario"
    val SHARED_TOKEN= "token"
    val SHARED_DATA= "data"


    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveToken(token: String){
        storage.edit().putString(SHARED_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return storage.getString(SHARED_TOKEN, "")

    }

    fun saveData(data: String){
        storage.edit().putString(SHARED_DATA, data).apply()
    }

    fun getData():String?{
        return storage.getString(SHARED_DATA, "")
    }

    fun clearToken(){
        if(storage.contains(SHARED_TOKEN)){
            storage.edit().remove(SHARED_TOKEN).apply()
        }
    }
}