package com.example.tycep_fe.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.ChatAdapter
import com.example.tycep_fe.databinding.FragmentHomeBinding
import com.example.tycep_fe.databinding.NavHeaderPrincipalBinding
import com.example.tycep_fe.models.Chat
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.viewModels.UserViewModel

class HomeFragment : Fragment() {
    private lateinit var userViewModel: ViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toolbar.setNavigationOnClickListener{
            binding.drawerLayout.openDrawer(binding.navView)
        }

        val navHeaderBinding = NavHeaderPrincipalBinding.bind(binding.navView.getHeaderView(0))
        navHeaderBinding.tvName.text="hola"
        navHeaderBinding.tvUsername.text="adios"




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mensajesChat1 = setOf(
            Mensaje(1, 1, "Hola", "2024-04-23", "Usuario1"),
            Mensaje(2, 1, "¿Cómo estás?", "2024-04-23", "Usuario2")
        )

        val mensajesChat2 = setOf(
            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario4")
        )

        val mensajesChat3 = setOf(
            Mensaje(5, 3, "Buenos días", "2024-04-21", "Usuario5"),
            Mensaje(6, 3, "¿Cómo va todo?", "2024-04-21", "Usuario6")
        )

        // Crear la lista de chats
        val chats = setOf(
            Chat(1, "Chat1", true, mensajesChat1),
            Chat(2, "Chat2", false, mensajesChat2),
            Chat(3, "Chat3", true, mensajesChat3)
        )

        //findNavController().navigate(R.id.chat)
        //initReciclerView(chats)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) { profesor ->
            println("Llega: $profesor")

            profesor?.let {
                // El profesor está disponible, navega al nuevo Fragmento
                println("Profesor en home: "+ profesor)
                initReciclerView(profesor.chats!!)
            } ?: run {
                // Manejar el caso en el que profesor es nulo
            }
        }
    }
    private fun initReciclerView(chats: Set<Chat>){
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerChats)
        recyclerView?.layoutManager= LinearLayoutManager(view?.context)
        recyclerView?.adapter= ChatAdapter(chats)

    }
}