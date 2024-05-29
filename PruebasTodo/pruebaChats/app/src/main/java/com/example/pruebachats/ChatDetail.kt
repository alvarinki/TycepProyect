package com.example.pruebachats

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebachats.databinding.FragmentChatDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalTime


class ChatDetail : Fragment() {

    private var chatId: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messages: MutableSet<Mensaje>
    private lateinit var nombreUsuario: String
    private lateinit var binding: FragmentChatDetailBinding
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chatId = it.getString("chatId")
            nombreUsuario= it.getString("nombreUsuario")!!
        }
        messages = mutableSetOf()
        messageAdapter = MessageAdapter(messages, nombreUsuario)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatDetailBinding.inflate(inflater, container, false)
        val view: View = binding.getRoot()

        view.getViewTreeObserver().addOnGlobalLayoutListener {
            val r = Rect()
            view.getWindowVisibleDisplayFrame(r)
            val screenHeight = view.getRootView().height
            val keypadHeight = screenHeight - r.bottom-20
            if (keypadHeight > screenHeight * 0.15) { // if more than 15% of the screen is occupied by the keyboard
                // Keyboard is opened
                binding.messageContainer.setPadding(0, 0, 0, keypadHeight)
            } else {
                // Keyboard is closed
                binding.messageContainer.setPadding(0, 0, 0, 0)
            }
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerMensajes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = messageAdapter

        chatId?.let {
            obtenerMensajesDelChat(it)
        }

        binding.btEnvio.setOnClickListener {
            val contenidoMensaje = binding.edMensaje.text.toString()
            if (contenidoMensaje.isNotEmpty()) {
                val mensaje = Mensaje(
                    contenido = contenidoMensaje,
                    fecha = LocalDate.now().toString(), // Suponiendo que tienes una función para obtener la fecha actual
                    hora = LocalTime.now().toString(),   // Suponiendo que tienes una función para obtener la hora actual
                    nombreUsuario = nombreUsuario // El nombre de usuario pasado por argumento
                )
                // Luego, subes el mensaje a la base de datos Firebase
                subirMensajeAlChat(chatId!!, mensaje)
            } else {
                // El EditText está vacío, muestra un mensaje de error o haz lo que consideres necesario
            }
        }

    }

    private fun obtenerMensajesDelChat(chatId: String) {
        val mensajesRef = FirebaseDatabase.getInstance().getReference("Chat/$chatId/mensajes")

        mensajesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messages.clear()
                for (mensajeSnapshot in dataSnapshot.children) {
                    val mensaje = mensajeSnapshot.getValue(Mensaje::class.java)
                    mensaje?.let { messages.add(it) }
                }
                messageAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messages.size - 1)  // Scroll to the bottom to show the latest message
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores
            }
        })
    }

    private fun subirMensajeAlChat(chatId: String, mensaje: Mensaje) {
        val mensajesRef = database.getReference("Chat").child(chatId).child("mensajes")
        val nuevoMensajeRef = mensajesRef.push() // Genera una nueva clave única para el mensaje
        nuevoMensajeRef.setValue(mensaje)
//            .addOnSuccessListener {
//                // El mensaje se subió correctamente
//                // Puedes mostrar un mensaje de éxito o realizar otras acciones si lo deseas
//                Toast.makeText(requireContext(), "Funcionó", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e ->
//                // Ocurrió un error al subir el mensaje
//                // Muestra un mensaje de error o realiza acciones de manejo de errores
//            }
    }
}

