package com.example.tycep_fe.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.databinding.ChatsRecyclerBinding
import com.example.tycep_fe.models.Chat

class ChatViewHolder(view:View): RecyclerView.ViewHolder(view) {

    val binding= ChatsRecyclerBinding.bind(view)
    fun render(chat: Chat){
        binding.tvChatName.text=chat.nombreChat
        binding.tvLastMessage.text= chat.mensajes.toList()[chat.mensajes.size-1].toString()
    }
}