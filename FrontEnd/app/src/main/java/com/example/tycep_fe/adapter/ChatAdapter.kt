package com.example.tycep_fe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.example.tycep_fe.databinding.ChatItemBinding
import com.example.tycep_fe.fragments.HomeFragmentDirections
import com.example.tycep_fe.modelFB.ChatFB

class ChatAdapter(private var nombreUsuario:String, private val chats:MutableList<ChatFB>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats[position]
        holder.render(chat)
        holder.itemView.setOnClickListener {
            // Aquí puedes definir la acción al hacer clic en un elemento del RecyclerView
            // Por ejemplo, navegar a otro fragmento utilizando Navigation Component

            val action = HomeFragmentDirections.actionHomeFragmentToChatDetail(chatId = chat.id, nombreUsuario = nombreUsuario)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = chats.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(username: String,newChats: List<ChatFB>) {
        nombreUsuario=username
        // Actualiza los datos del adaptador con los nuevos chats
        chats.clear()
        chats.addAll(newChats)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(chat: ChatFB) {
            binding.tvChatName.text = chat.nombreChat

        }
    }
}
