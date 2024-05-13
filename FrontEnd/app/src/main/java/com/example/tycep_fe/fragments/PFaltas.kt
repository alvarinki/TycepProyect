package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.PAlumnosAdapter
import com.example.tycep_fe.adapter.PutFaltasAdapter
import com.example.tycep_fe.databinding.FragmentChatBinding
import com.example.tycep_fe.databinding.FragmentPFaltasBinding
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.services.FaltaApi
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import java.time.LocalDate

class PFaltas : Fragment() {

    lateinit var binding:FragmentPFaltasBinding
    private lateinit var userViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentPFaltasBinding.inflate(inflater, container, false)
        val root:View= binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs= Prefs(requireContext())
        val idCurso=prefs.getData()?.split(",")?.get(3)?.toInt()
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        (userViewModel as UserViewModel)._profesor.observe(requireActivity()){profesor ->
            if(profesor.cursos?.filter { curso -> curso.id==idCurso }?.get(0)?.alumnos==null){
                (userViewModel as UserViewModel).getAlumnosFromCurso(idCurso!!)
            }
        }

        (userViewModel as UserViewModel)._profesor.observe(requireActivity()){profesor ->
            profesor.cursos!!.filter { curso -> curso.id==idCurso }[0].alumnos.let {alumnos ->
                val recyclerView= view.findViewById<RecyclerView>(R.id.recyclerPutFaltas)
                recyclerView?.layoutManager= GridLayoutManager(view.context, 2)
                val adapter= PutFaltasAdapter(alumnos, requireContext())
                recyclerView?.adapter= adapter

                binding.floatingButton.setOnClickListener{

                    val faltas:List<Falta> = generateAbscenses(adapter.getFaltas())
                    val alumnoViewModel= ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
                    val token:String= prefs.getToken().toString()
                    println(faltas)
                    alumnoViewModel.putFaltas(faltas, token)
                    //Comentado para evitar bucle durante pruebas
                    findNavController().navigate(R.id.action_PFaltas_to_homeFragment)
                }
            }


        }
//                val listaAlumnos = setOf(
//            Alumno(
//                id = 1,
//                nombre = "Juan",
//                apellidos = "Pérez",
//                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 1,
//                faltas = null
//            ),
//            Alumno(
//                id = 2,
//                nombre = "María",
//                apellidos = "González",
//                foto="https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 1,
//                faltas = null
//            ),
//            Alumno(
//                id = 3,
//                nombre = "Pedro",
//                apellidos = "López",
//                foto="https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 1,
//                faltas = null
//            ),
//            Alumno(
//                id = 4,
//                nombre = "Ana",
//                apellidos = "Martínez",
//                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 1,
//                faltas = null
//            ),
//            Alumno(
//                id = 5,
//                nombre = "Luis Elustondo",
//                apellidos = "Sánchez Segovia",
//                foto = "https://www.lucaedu.com/wp-content/uploads/2022/02/alumnos-motivados.jpg",
//                idCurso = 1,
//                faltas = null
//            )
//        )
//
//        val recyclerView= view.findViewById<RecyclerView>(R.id.recyclerPutFaltas)
//        recyclerView?.layoutManager= GridLayoutManager(view.context, 2)
//        val adapter= PutFaltasAdapter(listaAlumnos, requireContext())
//        recyclerView?.adapter= adapter
//
//        binding.floatingButton.setOnClickListener{
//
//            val faltas:List<Falta> = generateAbscenses(adapter.getFaltas())
//            val alumnoViewModel= ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
//            val token:String= prefs.getToken().toString()
//            println(faltas)
//            alumnoViewModel.putFaltas(faltas, token)
//            //Comentado para evitar bucle durante pruebas
//            findNavController().navigate(R.id.action_PFaltas_to_homeFragment)
//        }
    }


    private fun generateAbscenses(chuletaFaltas:List<String>):List<Falta>{
        var faltasDefinitivas: MutableList<Falta> = emptyList<Falta>().toMutableList()
        val fecha:String= LocalDate.now().toString()
        val hora:Int= prefs.getData()!!.split(",")[1].toInt()
        val asignatura:String= prefs.getData()!!.split(",")[2]
        for(chuleta in chuletaFaltas){
            val justificacion = chuleta.substring(0, 1)

            val booleano = justificacion=="J"

            val idAlumno= chuleta.substring(1).toInt()
            println("IdAlumno: $idAlumno")
            val falta=Falta(null, hora, asignatura, fecha, idAlumno, booleano)
            println(falta)
            faltasDefinitivas.add(falta)
        }

        return faltasDefinitivas
    }

}