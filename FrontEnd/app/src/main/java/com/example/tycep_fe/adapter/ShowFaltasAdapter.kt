package com.example.pruebapantallas

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FaltasItemBinding
import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.viewModels.AlumnoViewModel

class ShowFaltasAdapter(
    private var faltas: LinkedHashSet<Falta>,
    private val alumnoViewModel: AlumnoViewModel,
    private val userType: String
) : RecyclerView.Adapter<ShowFaltasAdapter.ItemViewHolder>() {
    private val userTypeCondition: Boolean = userType == "TutorLegal"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowFaltasAdapter.ItemViewHolder {
        val binding =
            FaltasItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: ShowFaltasAdapter.ItemViewHolder, position: Int) {
        val falta = faltas.toList()[position]
        holder.render(falta)
        if (userTypeCondition) {
            holder.binding.btnAction1.isEnabled = false
            holder.binding.btnAction1.visibility = View.GONE
            holder.binding.btnAction2.isEnabled = false
            holder.binding.btnAction2.visibility = View.GONE
        } else {
            holder.binding.btnAction1.setOnClickListener {

            }
            // Añade la lógica para el segundo botón si es necesario
            holder.binding.btnAction2.setOnClickListener {
                val absolutePosition = holder.adapterPosition // Obtener la posición absoluta
                if (absolutePosition != RecyclerView.NO_POSITION) { // Verificar si la posición es válida
                    confirmAndRemoveFalta(absolutePosition, holder.itemView.context, falta)
                }
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

    inner class ItemViewHolder(val binding: FaltasItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun render(falta: Falta) {
            binding.tvText1.text = falta.asignatura
            binding.tvText2.text = falta.fecha +" Hora: "+falta.hora
            binding.tvText3.text = if (falta.justificada) "Justificada" else "Injustificada"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun actualizarFaltas(nuevasFaltas: Set<Falta>) {
        this.faltas = nuevasFaltas.sortedBy { f -> f.fecha } as LinkedHashSet<Falta>
        notifyDataSetChanged()
    }
}
