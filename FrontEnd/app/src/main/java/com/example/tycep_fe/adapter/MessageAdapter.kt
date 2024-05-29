package com.example.tycep_fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R

import com.example.tycep_fe.databinding.MessageItemBinding
import com.example.tycep_fe.modelFB.MensajeFB

class MessageAdapter(private val messages: MutableSet<MensajeFB>, private val nombreUsuario: String) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.toList()[position]
        println("Mensajes "+message)
        holder.render(message)

        val tvUser = holder.itemView.findViewById<TextView>(R.id.tvUser)
        val tvMessage = holder.itemView.findViewById<TextView>(R.id.tvmessage)

        val layoutParamsUser = tvUser.layoutParams as ConstraintLayout.LayoutParams
        val layoutParamsMessage = tvMessage.layoutParams as ConstraintLayout.LayoutParams

        if (message.nombreUsuario == nombreUsuario) {
            layoutParamsUser.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParamsUser.startToStart = ConstraintLayout.LayoutParams.UNSET
            layoutParamsUser.horizontalBias = 1f
            layoutParamsMessage.horizontalBias = 1f
        } else {
            layoutParamsUser.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParamsUser.endToEnd = ConstraintLayout.LayoutParams.UNSET
            layoutParamsUser.horizontalBias = 0.0f
            layoutParamsMessage.horizontalBias = 0.0f
        }

        tvUser.layoutParams = layoutParamsUser
        tvMessage.layoutParams = layoutParamsMessage
    }

    inner class MessageViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun render(mensaje: MensajeFB) {
            binding.tvmessage.text = mensaje.contenido
            binding.tvUser.text = mensaje.nombreUsuario
        }
    }
}
