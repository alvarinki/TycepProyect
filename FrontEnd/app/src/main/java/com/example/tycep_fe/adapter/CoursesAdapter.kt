package com.example.tycep_fe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.OnespanItemBinding

import com.example.tycep_fe.models.Curso

class CoursesAdapter (private val courses: Set<Curso>, private val context: Context): RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val binding= OnespanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesViewHolder(binding)
    }

    override fun getItemCount(): Int = courses.size

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val course:Curso= courses.toList()[position]
        holder.render(course)

        holder.itemView.setOnClickListener{
            println(holder.itemView.context)
            TokenUsuarioApplication.prefs = Prefs(context)
            TokenUsuarioApplication.prefs.saveData(course.id.toString())
            holder.itemView.findNavController().navigate(R.id.action_cursos_to_recyclerAlumnos)
        }
    }

    inner class CoursesViewHolder(private val binding:OnespanItemBinding): RecyclerView.ViewHolder(binding.root){
        fun render(curso: Curso){
            binding.tvCourse.text= curso.nombre
            //binding.ivCourse=editar bd para tener foto
        }
    }
}
