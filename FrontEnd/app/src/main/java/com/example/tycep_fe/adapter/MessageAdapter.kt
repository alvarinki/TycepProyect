package com.example.tycep_fe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.databinding.MessageRecyclerBinding
import com.example.tycep_fe.models.Mensaje

class MessageAdapter(private val messages:Set<Mensaje>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message= messages.toList()[position]
        holder.render(message)

    }

    inner class MessageViewHolder(private val binding: MessageRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        fun render(mensaje: Mensaje){
            binding.tvmessage.text=mensaje.contenido
            binding.tvUser.text= mensaje.nombreUsuario
        }
    }
}