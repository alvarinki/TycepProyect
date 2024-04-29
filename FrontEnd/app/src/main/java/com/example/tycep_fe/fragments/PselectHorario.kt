package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.ScheduleAdapter
import com.example.tycep_fe.models.Asignatura
import com.example.tycep_fe.models.Dia
import com.example.tycep_fe.models.Horario


class PselectHorario : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pselect_horario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val horarios = setOf(
            Horario(
                1,
                Asignatura(1, "Matemáticas"),
                Dia.L,
                8,
                101,
                101
            ),
            Horario(
                2,
                Asignatura(2, "Historia"),
                Dia.J,
                10,
                102,
                102
            ),
            Horario(
                3,
                Asignatura(3, "Ciencias"),
                Dia.L,
                9,
                103,
                103
            ),
            Horario(
                4,
                Asignatura(4, "Literatura"),
                Dia.M,
                11,
                104,
                104
            ),
            Horario(
                5,
                Asignatura(5, "Inglés"),
                Dia.L,
                12,
                105,
                105
            )
        )
        val recyclerView= view.findViewById<RecyclerView>(R.id.recyclerHorarios)
        recyclerView?.layoutManager= LinearLayoutManager(this.context)
        val adapter= ScheduleAdapter(horarios, requireContext())
        recyclerView?.adapter= adapter
    }
}
