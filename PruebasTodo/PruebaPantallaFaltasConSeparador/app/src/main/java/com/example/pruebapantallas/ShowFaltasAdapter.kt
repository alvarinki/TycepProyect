import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebapantallas.Falta
import com.example.pruebapantallas.R
import com.example.pruebapantallas.databinding.ShowfaltaItemBinding

class ShowFaltasAdapter(private val faltas: LinkedHashSet<Falta>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_FALTA = 0
        private const val VIEW_TYPE_DATE_SEPARATOR = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FALTA -> {
                val binding = ShowfaltaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FaltaViewHolder(binding)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.separator_item, parent, false)
                DateSeparatorViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FaltaViewHolder -> {
                val falta = getFalta(position)
                holder.render(falta)
                holder.itemView.findViewById<ImageButton>(R.id.ivEliminar).setOnClickListener {
                    confirmAndRemoveFalta(falta!!, holder.itemView.context)
                }
            }
            is DateSeparatorViewHolder -> {
                val separatorDate = getDateSeparator(position)
                holder.bindDate(separatorDate)
            }
        }
    }

    override fun getItemCount(): Int {
        return faltas.size + getDateSeparatorCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isDateSeparatorPosition(position)) {
            VIEW_TYPE_DATE_SEPARATOR
        } else {
            VIEW_TYPE_FALTA
        }
    }

    private fun isDateSeparatorPosition(position: Int): Boolean {
        if (position == 0) return true
        val prevFalta = getFalta(position - 1)
        val currentFalta = getFalta(position)
        return prevFalta?.fecha != currentFalta?.fecha
    }

    private fun getDateSeparator(position: Int): String {
        val falta = getFalta(position)
        return falta?.fecha ?: ""
    }

    private fun getFalta(position: Int): Falta? {
        var faltaIndex = 0
        val separatorSet = HashSet<String>()
        for (falta in faltas) {
            if (!separatorSet.contains(falta.fecha)) {
                separatorSet.add(falta.fecha)
                if (faltaIndex == position) return null
                faltaIndex++
            }
            if (faltaIndex == position) return falta
            faltaIndex++
        }
        return null
    }

    private fun getDateSeparatorCount(): Int {
        val separatorSet = HashSet<String>()
        faltas.forEach { falta -> separatorSet.add(falta.fecha) }
        return separatorSet.size
    }

    private fun removeFalta(falta: Falta) {
        faltas.remove(falta)
        notifyDataSetChanged()
    }

    fun confirmAndRemoveFalta(falta: Falta, context: Context) {
        AlertDialog.Builder(context)
            .setMessage("¿Estás seguro de que quieres eliminar esta falta?")
            .setNegativeButton("No", null)
            .setPositiveButton("Sí") { _, _ ->
                removeFalta(falta)
            }
            .show()
    }

    inner class FaltaViewHolder(private val binding: ShowfaltaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun render(falta: Falta?) {
            if (falta != null) {
                binding.tvFecha.text = falta.fecha
                binding.tvAsignatura.text = falta.asignatura
                if (falta.justificada) binding.tvJustificacion.text = "J"
                else binding.tvJustificacion.text = "IN"
                binding.tvHora.text = falta.hora.toString()

                //Esto para los padres que la pantalla va a ser la misma
                binding.ivEditar.visibility = View.INVISIBLE
                binding.ivEditar.isEnabled = false
            }
        }
    }

    inner class DateSeparatorViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindDate(date: String) {
            val tvDateSeparator: TextView = itemView.findViewById(R.id.tvDateSeparator)
            tvDateSeparator.text = date
        }
    }
}
