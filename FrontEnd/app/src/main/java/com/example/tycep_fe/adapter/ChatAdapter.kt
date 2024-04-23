package com.example.tycep_fe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.ChatsRecyclerBinding
import com.example.tycep_fe.models.Chat

class ChatAdapter(private val chats:Set<Chat>) :  RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatsRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat= chats.toList()[position]
        holder.render(chat)
        holder.itemView.setOnClickListener{
            println(holder.itemView.context)
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_chat)
        }
    }

    override fun getItemCount(): Int =chats.size




    inner class ViewHolder(private val binding: ChatsRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        fun render(chat: Chat){
            binding.tvChatName.text=chat.nombreChat.toString()
            binding.tvLastMessage.text= chat.mensajes.toList()[chat.mensajes.size-1].contenido
        }
    }
}