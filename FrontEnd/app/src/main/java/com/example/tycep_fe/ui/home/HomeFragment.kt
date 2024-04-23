package com.example.tycep_fe.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.ChatAdapter
import com.example.tycep_fe.databinding.FragmentHomeBinding
import com.example.tycep_fe.models.Chat
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.viewModels.UserViewModel

class HomeFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
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



//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



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


        initReciclerView(chats)
//        userViewModel.profesor.observe(this) { profesor ->
//            println("Llega")
//            initReciclerView(profesor?.chats!!)
//
//        }
    }
    fun initReciclerView(chats: Set<Chat>){
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerChats)
        recyclerView?.layoutManager= LinearLayoutManager(view?.context)
        recyclerView?.adapter= ChatAdapter(chats)
    }
}