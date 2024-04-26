package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentInicioSesionBinding
import com.example.tycep_fe.databinding.FragmentShowStudentBinding


class showStudent : Fragment() {

    private var _binding: FragmentShowStudentBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentShowStudentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.btnToAbscenses?.setOnClickListener{
            findNavController().navigate(R.id.action_showStudent_to_faltas)
        }
    }
}