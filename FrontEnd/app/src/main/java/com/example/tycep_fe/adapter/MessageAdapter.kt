package com.example.tycep_fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.databinding.MessageFromBinding


import com.example.tycep_fe.databinding.MessageToBinding
import com.example.tycep_fe.modelFB.MensajeFB

class MessageAdapter(private val messages: MutableSet<MensajeFB>, private val nombreUsuario: String) :
     RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messageTo=1
    private val messageFrom=2
    override fun getItemViewType(position: Int): Int {
        val message = messages.elementAt(position)
        return if(message.nombreUsuario==nombreUsuario) messageTo
         else messageFrom
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == messageTo) {
            val binding = MessageToBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MessageToViewHolder(binding)
        } else {
            val binding = MessageFromBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MessageFromViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages.elementAt(position)
        if (holder is MessageToViewHolder) {
            holder.bind(message)
        } else if (holder is MessageFromViewHolder) {
            holder.bind(message)
        }
    }

//    inner class MessageViewHolder(private val binding: MessageItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun render(mensaje: MensajeFB) {
//            binding.tvmessage.text = mensaje.contenido
//            binding.tvUser.text = mensaje.nombreUsuario
//        }
//    }

    inner class MessageToViewHolder(private val binding: MessageToBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MensajeFB) {
            binding.toMsgTime.text=message.hora + " "+ message.fecha
            binding.tvToUser.text=message.contenido
        }
    }

    inner class MessageFromViewHolder(private val binding: MessageFromBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MensajeFB) {
            binding.fromMsgTime.text=message.hora + " "+ message.fecha
            binding.tvFromUser.text=message.contenido
        }
    }
}
