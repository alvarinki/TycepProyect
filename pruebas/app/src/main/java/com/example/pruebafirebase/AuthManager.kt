package com.example.pruebafirebase

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


sealed class AuthRes<out T>{
    data class Success<T>(val data: T): AuthRes<T>()
    data class Error(val errorMessage: String): AuthRes<Nothing>()
}



class AuthManager (private val context: Context){
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }
}

