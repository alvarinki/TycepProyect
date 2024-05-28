package com.example.tycep_fe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication
import com.example.tycep_fe.R

import com.example.tycep_fe.databinding.TwospanItemBinding
import com.example.tycep_fe.models.Alumno
import com.squareup.picasso.Picasso

class PutFaltasAdapter(private val alumnos: Set<Alumno>) :
    RecyclerView.Adapter<PutFaltasAdapter.AlumnosViewHolder>() {

    private var faltas: MutableList<String> = emptyList<String>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnosViewHolder {
        val binding = TwospanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlumnosViewHolder(binding)
    }

    override fun getItemCount(): Int = alumnos.size


    fun getFaltas(): List<String> {
        return faltas.toList()
    }

    override fun onBindViewHolder(holder: AlumnosViewHolder, position: Int) {
        val alumno = alumnos.toList()[position]
        holder.render(alumno)
        var color: Int = 0

        holder.itemView.setOnClickListener {
            val layoutParams = holder.itemView.layoutParams
            layoutParams.width -= 70// Reducir el ancho en un 5%
            layoutParams.height -= 70 // Reducir la altura en un 5%
            holder.itemView.layoutParams = layoutParams

            if (color == 0) {
                holder.itemView.findViewById<LinearLayout>(R.id.backTwoSpan)
                    .setBackgroundColor(Color.RED)
                color = 1
                faltas.add("I" + alumno.id)
            } else if (color == 1) {
                holder.itemView.findViewById<LinearLayout>(R.id.backTwoSpan)
                    .setBackgroundColor(Color.YELLOW)
                color = 2

                faltas.remove("I" + alumno.id)
                faltas.add("J" + alumno.id)
            } else {
                holder.itemView.findViewById<LinearLayout>(R.id.backTwoSpan)
                    .setBackgroundColor(Color.WHITE)

                faltas.remove("J" + alumno.id)
                color = 0
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