package com.example.tycep_fe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.ChatItemBinding
import com.example.tycep_fe.databinding.ScheduleItemBinding
import com.example.tycep_fe.models.Chat
import com.example.tycep_fe.models.Horario

class ScheduleAdapter(private val schedules: Set<Horario>, private val context: Context): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: ScheduleItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun render(horario: Horario){
            binding.tvNombre.text=horario.asignatura.nombre
            binding.tvDescripcion.text= "Aula ${horario.aula}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = schedules.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule= schedules.toList()[position]
        holder.render(schedule)
        holder.itemView.setOnClickListener{
            prefs= Prefs(context)
            prefs.saveData(schedule.id.toString()+","+schedule.hora.toString()+","+schedule.asignatura.nombre+","+schedule.idCurso)
            holder.itemView.findNavController().navigate(R.id.action_pselectHorario_to_PFaltas)
        }
    }
}