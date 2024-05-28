package com.example.pruebapantallas

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.ShowfaltaItemBinding
import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.viewModels.AlumnoViewModel

class ShowFaltasAdapter(
    private val faltas: LinkedHashSet<Falta>,
    private val alumnoViewModel: AlumnoViewModel,
    private val context: Context
) : RecyclerView.Adapter<ShowFaltasAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowFaltasAdapter.ItemViewHolder {
        val binding =
            ShowfaltaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowFaltasAdapter.ItemViewHolder, position: Int) {
        val falta = faltas.toList()[position]
        holder.render(falta)

        holder.itemView.findViewById<ImageButton>(R.id.ivEliminar).setOnClickListener {
            val absolutePosition = holder.adapterPosition // Obtener la posición absoluta
            if (absolutePosition != RecyclerView.NO_POSITION) { // Verificar si la posición es válida
                confirmAndRemoveFalta(absolutePosition, holder.itemView.context, falta)
            }
        }
    }

    override fun getItemCount(): Int = faltas.size

    private fun removeFalta(position: Int) {
        faltas.remove(faltas.toList()[position])
        notifyItemRemoved(position)
    }

    fun confirmAndRemoveFalta(position: Int, context: Context, falta: Falta) {
        AlertDialog.Builder(context)
            .setMessage("¿Estás seguro de que quieres eliminar esta falta?")
            .setNegativeButton("No", null)
            .setPositiveButton("Sí") { _, _ ->
                removeFalta(position)
                val prefs = Prefs(context)
                val token = prefs.getToken().toString()
                alumnoViewModel.deleteFaltaFromAlumno(falta, token)
            }
            .show()
    }

    inner class ItemViewHolder(private val binding: ShowfaltaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun render(falta: Falta) {
            binding.tvFecha.text = falta.fecha
            binding.tvAsignatura.text = falta.asignatura
            if (falta.justificada) binding.tvJustificacion.text = "J"
            else binding.tvJustificacion.text = "IN"
            binding.tvHora.text = falta.hora.toString()

            //Esto pa los padres que la pantalla va a ser la misma
            binding.ivEditar.visibility = View.INVISIBLE
            binding.ivEditar.isEnabled = false

        }
    }
}


