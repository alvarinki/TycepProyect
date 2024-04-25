package com.example.tycep_fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.MessageItemBinding
import com.example.tycep_fe.models.Mensaje

class MessageAdapter(private val messages:MutableSet<Mensaje>, private val nombreUsuario:String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message= messages.toList()[position]
        holder.render(message)
        val tvUser = holder.itemView.findViewById<TextView>(R.id.tvUser)
        val tvMessage = holder.itemView.findViewById<TextView>(R.id.tvmessage)

        val layoutParamsUser = tvUser.layoutParams as ConstraintLayout.LayoutParams
        val layoutParamsMessage = tvMessage.layoutParams as ConstraintLayout.LayoutParams

        if (message.nombreUsuario == nombreUsuario) {
            // Alinea el TextView del usuario a la derecha
            layoutParamsUser.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParamsUser.startToStart = ConstraintLayout.LayoutParams.UNSET // Elimina la restricción de alineación al inicio
            layoutParamsUser.horizontalBias = 1f // Alinea el TextView completamente a la derecha
            layoutParamsMessage.horizontalBias = 1f

            // Alinea el TextView del mensaje a la izquierda
//            layoutParamsMessage.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
//            layoutParamsMessage.endToEnd = tvUser.id // Alinea el final del TextView del mensaje al principio del TextView del usuario
        } else {
            // Alinea el TextView del usuario a la izquierda
            layoutParamsUser.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParamsUser.endToEnd = ConstraintLayout.LayoutParams.UNSET // Elimina la restricción de alineación al final
            layoutParamsUser.horizontalBias = 0.0f // Alinea el TextView completamente a la izquierda
            layoutParamsMessage.horizontalBias = 0.0f
//            // Alinea el TextView del mensaje a la derecha
//            layoutParamsMessage.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
//            layoutParamsMessage.startToStart = tvUser.id // Alinea el inicio del TextView del mensaje al final del TextView del usuario
        }

// Aplica los cambios en las restricciones de los TextViews
        tvUser.layoutParams = layoutParamsUser
        tvMessage.layoutParams = layoutParamsMessage
    }

    inner class MessageViewHolder(private val binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun render(mensaje: Mensaje){
            binding.tvmessage.text=mensaje.contenido
            binding.tvUser.text= mensaje.nombreUsuario
        }
    }
}