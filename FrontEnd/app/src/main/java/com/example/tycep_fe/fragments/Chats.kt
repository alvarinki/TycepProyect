package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.MessageAdapter
import com.example.tycep_fe.databinding.FragmentChatBinding
import com.example.tycep_fe.models.Chat
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.ui.ReverseLinearLayoutManager
import com.example.tycep_fe.viewModels.UserViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


class Chats : Fragment() {
    private lateinit var userViewModel: ViewModel
    private lateinit var binding: FragmentChatBinding
    private lateinit var adapter: MessageAdapter
    private lateinit var selectedChatMessages: MutableList<Mensaje>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) { profesor ->
            val idChat = prefs.getData().toString().toInt()
            selectedChatMessages = profesor.chats?.filter { chat -> chat.id == idChat }!![0].mensajes.toMutableList()
            //initRecyclerView(selectedChatMessages)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerMensajes)
            recyclerView?.layoutManager = LinearLayoutManager(this.context)
            //ReverseLinearLayoutManager(this.requireContext())
            val sortedMessages: LinkedHashSet<Mensaje> = java.util.LinkedHashSet<Mensaje>()
            sortedMessages.addAll(selectedChatMessages.sortedBy { c -> c.id }.toSet())
            adapter = MessageAdapter(sortedMessages.toMutableSet(), profesor.usuario)
            recyclerView?.adapter = adapter
            binding.btEnvio.setOnClickListener {
                val messageContent: String = binding.edMensaje.text.toString()
                val newMessage =
                    Mensaje(null, 1, messageContent, LocalDate.now().toString(), profesor.usuario)
                selectedChatMessages.add(0, newMessage)

                adapter.notifyItemInserted(0)
                recyclerView?.smoothScrollToPosition(selectedChatMessages.size - 1)
                val token: String = prefs.getToken().toString()
                (userViewModel as UserViewModel).uploadMessage(newMessage, token)
                binding.edMensaje.text = null
            }
        }
        var dia = obtenerNombreDiaSemana(LocalDate.now())

//        val mensajesChat1 = mutableListOf(
//            Mensaje(1, 1, "Hola", "2024-04-23", "Usuario1"),
//            Mensaje(2, 1, "¿Cómo estás?", "2024-04-23", "Usuario1"),
//            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
//            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario1")
//        )


    }

    private fun obtenerNombreDiaSemana(localDate: LocalDate): String {
        val dayOfWeek = localDate.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es"))
    }
}