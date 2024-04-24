package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.MessageAdapter
import com.example.tycep_fe.databinding.FragmentChatBinding
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.ui.ReverseLinearLayoutManager
import com.example.tycep_fe.viewModels.UserViewModel
import java.time.LocalDate


class Chat : Fragment() {
    private lateinit var userViewModel: ViewModel
    private lateinit var binding:FragmentChatBinding
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentChatBinding.inflate(inflater, container, false)
        val root:View= binding.root
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner){ profesor ->
            println("Llega: $profesor")
            initRecyclerView(profesor.chats?.filter{ chat -> chat.id==1 }!![0].mensajes.toMutableList())
        }

        val mensajesChat1 = mutableListOf(
            Mensaje(1, 1, "Hola", "2024-04-23", "Usuario1"),
            Mensaje(2, 1, "¿Cómo estás?", "2024-04-23", "Usuario1"),
            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario1")
        )
        //initRecyclerView(mensajesChat1)
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerMensajes)
        recyclerView?.layoutManager= ReverseLinearLayoutManager(this.requireContext())
        adapter= MessageAdapter(mensajesChat1)
        recyclerView?.adapter= adapter
        binding.btEnvio.setOnClickListener{
            val mensajeNuevo:String= binding.edMensaje.text.toString()
            mensajesChat1.add(0, Mensaje(null, 1, mensajeNuevo, LocalDate.now().toString(), "Usuario12"))
            adapter.notifyItemInserted(0)
            recyclerView?.smoothScrollToPosition(mensajesChat1.size-1)
            binding.edMensaje.text=null
        }
    }

    private fun initRecyclerView(messages: MutableList<Mensaje>){

    }


}