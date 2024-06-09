package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.enableSavedStateHandles
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentHorarioBinding
import com.example.tycep_fe.models.Asignatura
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.models.Dia
import com.example.tycep_fe.models.Horario
import com.example.tycep_fe.viewModels.UserViewModel

class horario : Fragment() {
    private lateinit var userViewModel: ViewModel
    private var _binding: FragmentHorarioBinding?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHorarioBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val horarios = setOf(
//            Horario(id = 1, asignatura = Asignatura(id = 1, nombre = "Matemáticas"), dia = Dia.L, hora = 1, 1,1, 4),
//            Horario(id = 2, asignatura = Asignatura(id = 2, nombre = "Física"), dia = Dia.M, hora = 2, 1, 1, 4),
//            Horario(id = 3, asignatura = Asignatura(id = 3, nombre = "Química"), dia = Dia.X, hora = 3, 1, 1, 4),
//            Horario(id = 4, asignatura = Asignatura(id = 4, nombre = "Historia"), dia = Dia.J, hora = 4, 1, 1, 4),
//            Horario(id = 5, asignatura = Asignatura(id = 5, nombre = "Literatura"), dia = Dia.V, hora = 5, 1, 1, 4),
//            Horario(id = 6, asignatura = Asignatura(id = 6, nombre = "Biología"), dia = Dia.L, hora = 6, 1, 1, 4),
//            Horario(id = 7, asignatura = Asignatura(id = 7, nombre = "Geografía"), dia = Dia.M, hora = 1, 1, 2, 4),
//            Horario(id = 8, asignatura = Asignatura(id = 8, nombre = "Inglés"), dia = Dia.X, hora = 2, 1, 3, 4),
//            Horario(id = 9, asignatura = Asignatura(id = 9, nombre = "Educación Física"), dia = Dia.J, hora = 3, 1, 4, 4),
//            Horario(id = 10, asignatura = Asignatura(id = 10, nombre = "Arte"), dia = Dia.V, hora = 4, 1, 5, 4)
//        )
//
//        val curso5 = Curso(
//            id = 5,
//            nombre = "Curso de Arte",
//            alumnos = emptySet(),
//            horario = horarios
//        )
        //cargarHorarios(curso5.horario.filter { h -> h.idProfesor== 1 }.toSet())

        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) {
            (userViewModel as UserViewModel).getHorarioFromProfesor()
            (userViewModel as UserViewModel)._horarios.observe(viewLifecycleOwner) { horario ->
                println(horario)
                if (horario != null) cargarHorarios(horario)

            }
        }

        (userViewModel as UserViewModel)._tutorLegal.observe(viewLifecycleOwner) {
            val prefs = Prefs(requireContext())
            val idAlumno = prefs.getData()?.toInt()
            (userViewModel as UserViewModel).getHorarioFromAlumno(idAlumno!!, prefs.getToken()!!)
            (userViewModel as UserViewModel)._horarios.observe(viewLifecycleOwner) { horario ->
                if (horario != null) {
                    cargarHorarios(horario)
                }
            }
        }

    }

    private fun cargarHorarios(horarios: Set<Horario>) {

        for (horario in horarios) {

            val textViewId = resources.getIdentifier(
                "row${horario.hora}_col${horario.dia.ordinal + 1}",
                "id",
                activity?.packageName
            )
            val textView = view?.findViewById<TextView>(textViewId)
            val asignatura = when (horario.asignatura.nombre.split(" ")[0]) {
                "Mates" -> "Mat"
                "Geografía" -> "Geo"
                "Física" -> "FyQ"
                "Historia" -> "His"
                "Inglés" -> "Ing"
                "Química" -> "Qui"
                "Educación Física" -> "EdF"
                "Lengua" -> "LyL"
                "Sociales" -> "Soc"
                else -> "Error"
            }
            // Establecer el nombre de la asignatura en el TextView
            textView?.text = asignatura + "\n" + horario.aula

        }
    }

//    private fun cargarHorarioProfesor(idProfesor: Int, cursosProfesor:Set<Curso>):Set<Horario>{
//        var horarios: MutableList<Horario> = emptyList<Horario>().toMutableList()
//        for(cursos in cursosProfesor){
//            horarios.addAll(cursos.horario.filter { h -> h.idProfesor==idProfesor })
//        }
//        return horarios.toSet()
//    }

}