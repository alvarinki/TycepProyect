package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.enableSavedStateHandles
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentHorarioBinding
import com.example.tycep_fe.models.Asignatura
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.models.Dia
import com.example.tycep_fe.models.Horario

class horario : Fragment() {
    private lateinit var _binding: FragmentHorarioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHorarioBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val horarios = setOf(
            Horario(id = 1, asignatura = Asignatura(id = 1, nombre = "Matemáticas"), dia = Dia.L, hora = 1, 222,1),
            Horario(id = 2, asignatura = Asignatura(id = 2, nombre = "Física"), dia = Dia.M, hora = 2, 213, 1),
            Horario(id = 3, asignatura = Asignatura(id = 3, nombre = "Química"), dia = Dia.X, hora = 3, 453, 1),
            Horario(id = 4, asignatura = Asignatura(id = 4, nombre = "Historia"), dia = Dia.J, hora = 4, 126, 1),
            Horario(id = 5, asignatura = Asignatura(id = 5, nombre = "Literatura"), dia = Dia.V, hora = 5, 98, 1),
            Horario(id = 6, asignatura = Asignatura(id = 6, nombre = "Biología"), dia = Dia.L, hora = 6, 65, 1),
            Horario(id = 7, asignatura = Asignatura(id = 7, nombre = "Geografía"), dia = Dia.M, hora = 1, 13, 2),
            Horario(id = 8, asignatura = Asignatura(id = 8, nombre = "Inglés"), dia = Dia.X, hora = 2, 91, 3),
            Horario(id = 9, asignatura = Asignatura(id = 9, nombre = "Educación Física"), dia = Dia.J, hora = 3, 310, 4),
            Horario(id = 10, asignatura = Asignatura(id = 10, nombre = "Arte"), dia = Dia.V, hora = 4, 322, 5)
        )

        val curso5 = Curso(
            id = 5,
            nombre = "Curso de Arte",
            alumnos = emptySet(),
            horario = horarios
        )
        cargarHorarios(curso5.horario.filter { h -> h.idProfesor== 1 }.toSet())


    }
    private fun cargarHorarios(horarios: Set<Horario>) {
        val tableLayout = _binding.tableLayout
        // Iterar sobre los horarios
        for (horario in horarios) {
            // Obtener el TextView correspondiente en la tabla

            val textViewId = resources.getIdentifier("row${horario.hora}_col${horario.dia.ordinal + 1}", "id", activity?.packageName)
            val textView = view?.findViewById<TextView>(textViewId)
            val asignatura= when(horario.asignatura.nombre){
                "Matemáticas" -> "Mat"
                "Geografía" -> "Geo"
                "Física" -> "FyQ"
                "Inglés" -> "Ing"
                "Química" -> "Qui"
                "Educación Física" -> "EdF"
                "Literatura" -> "LyL"
                "Biología" -> "Bio"
                else -> "Nope"
            }
            // Establecer el nombre de la asignatura en el TextView
            textView?.text = asignatura + "\n" + horario.aula

        }
    }

    private fun cargarHorarioProfesor(idProfesor: Int, cursosProfesor:Set<Curso>):Set<Horario>{
        var horarios: MutableList<Horario> = emptyList<Horario>().toMutableList()
        for(cursos in cursosProfesor){
            horarios.addAll(cursos.horario.filter { h -> h.idProfesor==idProfesor })
        }
        return horarios.toSet()
    }

}