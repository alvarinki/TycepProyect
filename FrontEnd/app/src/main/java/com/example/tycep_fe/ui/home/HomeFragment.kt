package com.example.tycep_fe.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.ChatAdapter
import com.example.tycep_fe.databinding.FragmentHomeBinding
import com.example.tycep_fe.models.Chat
import com.example.tycep_fe.viewModels.UserViewModel

class HomeFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        println(userViewModel.profesor.value)

        userViewModel.profesor.observe(this) { profesor ->
            println("Llega")
            initReciclerView(profesor?.chats!!)

        }

//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initReciclerView(chats: Set<Chat>){
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerChats)
        recyclerView?.layoutManager= LinearLayoutManager(view?.context)
        recyclerView?.adapter= ChatAdapter(chats)
    }
}