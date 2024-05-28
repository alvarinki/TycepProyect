package com.example.tycep_fe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication
import com.example.tycep_fe.R

import com.example.tycep_fe.databinding.TwospanItemBinding
import com.example.tycep_fe.models.Alumno
import com.squareup.picasso.Picasso

class PAlumnosAdapter(
    private val alumnos: Set<Alumno>,
    private val context: Context,
    private val origen: String
) : RecyclerView.Adapter<PAlumnosAdapter.AlumnosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnosViewHolder {
        val binding = TwospanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlumnosViewHolder(binding)
    }

    override fun getItemCount(): Int = alumnos.size

    override fun onBindViewHolder(holder: AlumnosViewHolder, position: Int) {
        val alumno = alumnos.toList()[position]
        holder.render(alumno)

        holder.itemView.setOnClickListener {

            TokenUsuarioApplication.prefs = Prefs(context)
            TokenUsuarioApplication.prefs.saveData(alumno.id.toString())

            if (origen == "Home") {
                holder.itemView.findNavController().navigate(R.id.action_recyclerAlumnos_to_horario)
            } else if (origen == "Cursos") {
                holder.itemView.findNavController()
                    .navigate(R.id.action_recyclerAlumnos_to_showStudent)
            }
        }
    }

    inner class AlumnosViewHolder(private val binding: TwospanItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun render(alumno: Alumno) {
            binding.tvStudent.text = alumno.nombre + " " + alumno.apellidos
            Picasso.get().load(alumno.foto).into(binding.ivStudent)
        }
    }
}