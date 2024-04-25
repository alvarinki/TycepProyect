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
import com.example.tycep_fe.databinding.StudentsItemBinding
import com.example.tycep_fe.models.Alumno
import com.squareup.picasso.Picasso

class AlumnosAdapter (private val alumnos:Set<Alumno>, private val context: Context) :  RecyclerView.Adapter<AlumnosAdapter.AlumnosViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnosViewHolder {
        val binding = StudentsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlumnosViewHolder(binding)

    }

    override fun getItemCount(): Int = alumnos.size

    override fun onBindViewHolder(holder: AlumnosViewHolder, position: Int) {
        val alumno= alumnos.toList()[position]
        holder.render(alumno)

        holder.itemView.setOnClickListener{
            println(holder.itemView.context)
            TokenUsuarioApplication.prefs = Prefs(context)
            TokenUsuarioApplication.prefs.saveData(alumno.id.toString())
            holder.itemView.findNavController().navigate(R.id.action_recyclerAlumnos_to_showStudent

            )
        }
    }

    inner class AlumnosViewHolder(private val binding: StudentsItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun render(alumno: Alumno){
            binding.tvStudent.text=alumno.nombre+" "+alumno.apellidos
            Picasso.get().load(alumno.foto).into(binding.ivStudent)
        }
    }
}