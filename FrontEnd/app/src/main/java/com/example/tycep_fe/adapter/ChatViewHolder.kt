package com.example.tycep_fe.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.databinding.ChatItemBinding
import com.example.tycep_fe.models.Chat

class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ChatItemBinding.bind(view)
    fun render(chat: Chat) {
        binding.tvChatName.text = chat.nombreChat.toString()
        binding.tvLastMessage.text = chat.mensajes.toList()[chat.mensajes.size - 1].contenido
    }
}