package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentHomeBinding
import com.example.tycep_fe.databinding.FragmentSeleccionFaltasBinding

class seleccionFaltas : Fragment() {

    private var _binding: FragmentSeleccionFaltasBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeleccionFaltasBinding.inflate(inflater, container, false)
        val root: View = _binding!!.root

        _binding!!.btnPonerFaltas.setOnClickListener {
            findNavController().navigate(R.id.action_seleccionFaltas_to_pselectHorario)
        }

        _binding!!.btnFCurso.setOnClickListener {
            val action = seleccionFaltasDirections.actionSeleccionFaltasToCursos(origen = "Faltas")
            findNavController().navigate(action)
        }

        return root
    }
}