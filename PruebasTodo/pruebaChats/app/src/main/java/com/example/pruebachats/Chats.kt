package com.example.pruebachats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Chats : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        recyclerView = view.findViewById(R.id.recyclerChats)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = ChatAdapter(requireContext(), emptyList<Chat>().toMutableList()) // Adapter inicialmente vacío
        recyclerView.adapter = chatAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializar la RecyclerView después de que la vista se haya creado
        //initRecyclerView()
        obtenerChatsDeUsuario("tito")

    }

    private fun initRecyclerView() {
        val database = FirebaseDatabase.getInstance()
        val chatsRef = database.getReference("Chat")

        // Escuchar cambios en los chats
        chatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chatsList = mutableListOf<Chat>()
                for (chatSnapshot in dataSnapshot.children) {
                    val chat = chatSnapshot.getValue(Chat::class.java)
                    chat?.let { chatsList.add(it) }
                }
                chatAdapter.updateData(chatsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores
            }
        })
    }

    val database = FirebaseDatabase.getInstance()


    fun obtenerChatsDeUsuario(nombreUsuario: String) {
        val usuariosRef = database.getReference("Usuarios")

        // Primero, busca el ID del usuario usando su nombre de usuario
        usuariosRef.orderByChild("username").equalTo(nombreUsuario)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (usuarioSnapshot in dataSnapshot.children) {
                        // Obtenemos el ID del usuario
                        val usuarioId = usuarioSnapshot.key
                        println("Id de usuario: "+usuarioSnapshot.key)
                        // Ahora, con el ID del usuario, buscamos sus chats en la base de datos
                        if (usuarioId != null) {
                            val chatsRef = database.getReference("Chat")
                            chatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(chatsDataSnapshot: DataSnapshot) {
                                    val chatsList = mutableListOf<Chat>()
                                    for (chatSnapshot in chatsDataSnapshot.children) {
                                        val usuarios = chatSnapshot.child("usuarios")
                                        if (usuarios.hasChild(usuarioId)) {
                                            val chat = chatSnapshot.getValue(Chat::class.java)

                                            // Por ejemplo, imprimir el nombre del chat
                                            chat?.let {
                                                val chatId = chatSnapshot.key
                                                // Asignar el ID del chat al atributo "id" del objeto chat
                                                it.id = chatId!!
                                                chatsList.add(it) }
                                            val nombreChat = chat?.nombreChat
                                            println("Nombre del chat: $nombreChat")
                                        }
                                    }
                                    chatAdapter.updateData(chatsList)
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Manejo de errores
                                }
                            })
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejo de errores
                }
            })
    }
}
