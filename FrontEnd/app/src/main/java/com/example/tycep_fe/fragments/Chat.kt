package com.example.tycep_fe.fragments

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.MessageAdapter
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.ui.ReverseLinearLayoutManager
import com.example.tycep_fe.viewModels.UserViewModel


class Chat : Fragment() {
    private lateinit var userViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner){ profesor ->
            println("Llega: $profesor")
            initRecyclerView(profesor.chats?.filter{ chat -> chat.id==1 }!![0].mensajes)
        }

        val mensajesChat1 = setOf(
            Mensaje(1, 1, "Hola", "2024-04-23", "Usuario1"),
            Mensaje(2, 1, "¿Cómo estás?", "2024-04-23", "Usuario1"),
            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario1")
        )
        initRecyclerView(mensajesChat1)

    }

    private fun initRecyclerView(messages: Set<Mensaje>){
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerMensajes)
        recyclerView?.layoutManager= ReverseLinearLayoutManager(this.context!!)
        recyclerView?.adapter= MessageAdapter(messages)
    }
}