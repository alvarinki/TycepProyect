package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.PAlumnosAdapter
import com.example.tycep_fe.adapter.PutFaltasAdapter
import com.example.tycep_fe.models.Alumno

class PFaltas : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_p_faltas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                val listaAlumnos = setOf(
            Alumno(
                id = 1,
                nombre = "Juan",
                apellidos = "Pérez",
                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
                idCurso = 101,
                faltas = null
            ),
            Alumno(
                id = 2,
                nombre = "María",
                apellidos = "González",
                foto="https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
                idCurso = 101,
                faltas = null
            ),
            Alumno(
                id = 3,
                nombre = "Pedro",
                apellidos = "López",
                foto="https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
                idCurso = 102,
                faltas = null
            ),
            Alumno(
                id = 4,
                nombre = "Ana",
                apellidos = "Martínez",
                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
                idCurso = 102,
                faltas = null
            ),
            Alumno(
                id = 5,
                nombre = "Luis Elustondo",
                apellidos = "Sánchez Segovia",
                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
                idCurso = 103,
                faltas = null
            )
        )

        val recyclerView= view.findViewById<RecyclerView>(R.id.recyclerPutFaltas)
        recyclerView?.layoutManager= GridLayoutManager(view.context, 2)
        recyclerView?.adapter= PutFaltasAdapter(listaAlumnos, requireContext())
    }


    private fun initReciclerView(alumnos: Set<Alumno>){

    }

}