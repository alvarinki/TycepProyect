package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.AlumnosAdapter
import com.example.tycep_fe.adapter.CoursesAdapter
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.viewModels.UserViewModel

class Cursos : Fragment() {
    private lateinit var userViewModel: UserViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cursos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        userViewModel._profesor.observe(viewLifecycleOwner) { profesor ->
            profesor.cursos?.let {
                println("Cursos a última hora "+profesor.cursos)
                initReciclerView(profesor.cursos!!)
            }
        }

        val curso1 = Curso(
            id = 1,
            nombre = "Curso de Matemáticas",
            alumnos = emptySet(),
            horario = emptySet()
        )

        val curso2 = Curso(
            id = 2,
            nombre = "Curso de Historia",
            alumnos = emptySet(),
            horario = emptySet()
        )

        val curso3 = Curso(
            id = 3,
            nombre = "Curso de Ciencias",
            alumnos = emptySet(),
            horario = emptySet()
        )

        val curso4 = Curso(
            id = 4,
            nombre = "Curso de Literatura",
            alumnos = emptySet(),
            horario = emptySet()
        )

        val curso5 = Curso(
            id = 5,
            nombre = "Curso de Arte",
            alumnos = emptySet(),
            horario = emptySet()
        )

        val cursos: Set<Curso> = setOf(curso1, curso2, curso3, curso4, curso5)
        //initReciclerView(cursos)
    }

    private fun initReciclerView(courses: Set<Curso>){
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerCursos)
        recyclerView?.layoutManager= LinearLayoutManager(this.context)
        recyclerView?.adapter= CoursesAdapter(courses, requireContext())
    }
}