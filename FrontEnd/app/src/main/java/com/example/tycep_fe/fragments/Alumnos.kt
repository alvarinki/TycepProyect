package com.example.tycep_fe.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.PAlumnosAdapter
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.viewModels.UserViewModel


class Alumnos : Fragment() {
    private lateinit var userViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        val idCurso: Int? = prefs.getData()?.toInt()
        (userViewModel as UserViewModel).getAlumnosFromCurso(idCurso!!)
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) { profesor ->
            profesor?.let {
                initReciclerView(profesor.cursos?.filter { c -> c.id== idCurso}!![0].alumnos)
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alumnos, container, false)
    }

    override fun onResume() {
        super.onResume()
        val idCurso: Int? = prefs.getData()?.toInt()
        (userViewModel as UserViewModel).getAlumnosFromCurso(idCurso!!)
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) { profesor ->
            profesor?.let {
                initReciclerView(profesor.cursos?.filter { c -> c.id== idCurso}!![0].alumnos)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val listaAlumnos = setOf(
//            Alumno(
//                id = 1,
//                nombre = "Juan",
//                apellidos = "Pérez",
//                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 101,
//                faltas = null
//            ),
//            Alumno(
//                id = 2,
//                nombre = "María",
//                apellidos = "González",
//                foto="https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 101,
//                faltas = null
//            ),
//            Alumno(
//                id = 3,
//                nombre = "Pedro",
//                apellidos = "López",
//                foto="https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 102,
//                faltas = null
//            ),
//            Alumno(
//                id = 4,
//                nombre = "Ana",
//                apellidos = "Martínez",
//                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 102,
//                faltas = null
//            ),
//            Alumno(
//                id = 5,
//                nombre = "Luis Elustondo",
//                apellidos = "Sánchez Segovia",
//                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 103,
//                faltas = null
//            )
//        )
//
//        initReciclerView(listaAlumnos)
    }


    private fun initReciclerView(alumnos: Set<Alumno>){
        val recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerAlumnos)
        recyclerView?.layoutManager= GridLayoutManager(view?.context, 2)
        recyclerView?.adapter= PAlumnosAdapter(alumnos, requireContext())
    }

}