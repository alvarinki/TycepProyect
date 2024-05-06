package com.example.pruebafirebase

import android.content.Context
import androidx.compose.runtime.key
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class RealtimeManager(context: Context) {
    private val databaseReference: DatabaseReference= FirebaseDatabase.getInstance().reference.child("users")
    private val authManager = AuthManager(context)
    fun addUser(user: User){

        val key= databaseReference.push().key
        if(key !=null){databaseReference.child(key).setValue(user)
    }
    }
    fun deleteUser(key: String){
        databaseReference.child(key).removeValue()
    }

    fun getUsersFlow(): Flow<List<User>> {
        val idFilter= authManager.getCurrentUser()?.uid
        val flow= callbackFlow {
            val listener = databaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = snapshot.children.mapNotNull {
                        val user = snapshot.getValue(User::class.java)
                        snapshot.key.let { user?.copy(id = it) }
                    }
                    trySend(users.filter { it.uid == idFilter })
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })

            awaitClose { databaseReference.removeEventListener(listener) }
        }
        return flow
    }

}
