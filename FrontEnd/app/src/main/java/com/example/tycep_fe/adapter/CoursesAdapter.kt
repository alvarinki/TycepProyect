package com.example.tycep_fe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.OnespanItemBinding
import com.example.tycep_fe.fragments.CursosDirections
import com.example.tycep_fe.fragments.HomeFragmentDirections
import com.example.tycep_fe.fragments.showStudentDirections

import com.example.tycep_fe.models.Curso
import kotlinx.coroutines.currentCoroutineContext

class CoursesAdapter(
    private val courses: Set<Curso>,
    private val context: Context,
    private val origen: String
) : RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val binding = OnespanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesViewHolder(binding)
    }

    override fun getItemCount(): Int = courses.size

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val course: Curso = courses.toList()[position]
        holder.render(course)
        holder.itemView.setOnClickListener {

            TokenUsuarioApplication.prefs = Prefs(context)
            TokenUsuarioApplication.prefs.saveData(course.id.toString())
            val destino = "Cursos"
            if (origen == "Home") {
                val action = CursosDirections.actionCursosToRecyclerAlumnos(destino)
                holder.itemView.findNavController().navigate(action)
            } else if (origen == "Faltas") {
                val action = CursosDirections.actionCursosToFaltasAlumno(destino)
                holder.itemView.findNavController().navigate(action)
            }
            //holder.itemView.findNavController().navigate(R.id.action_se)
            //holder.itemView.findNavController().navigate(R.id.action_cursos_to_recyclerAlumnos)
        }
    }

    inner class CoursesViewHolder(private val binding: OnespanItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(curso: Curso) {
            binding.tvCourse.text = curso.nombre
            //binding.ivCourse=editar bd para tener foto
        }
    }
}
