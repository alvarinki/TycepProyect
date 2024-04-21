package com.example.tycep_fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.models.Chat

class ChatAdapter(private val chats:Set<Chat>) :  RecyclerView.Adapter<ChatViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ChatViewHolder(layoutInflater.inflate(R.layout.chats_recycler, parent, false))
    }

    override fun getItemCount(): Int =chats.size


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = chats.toList()[position]
        holder.render(item)
    }
}