package com.example.tycep_fe.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebapantallas.ShowFaltasAdapter
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentFaltasAlumnoBinding
import com.example.tycep_fe.models.Alumno
import com.example.tycep_fe.models.Falta
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import com.google.firebase.firestore.auth.User

class FaltasAlumno : Fragment() {
    private lateinit var binding: FragmentFaltasAlumnoBinding

    //private lateinit var alumno: Alumno
    private lateinit var alumnoViewModel: AlumnoViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private var userType:String=""
    val args: FaltasAlumnoArgs by navArgs()
    private lateinit var origen: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaltasAlumnoBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        userViewModel._tutorLegal.observe(viewLifecycleOwner){
           userType="TutorLegal"
        }
        origen = args.origen
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val prefs = Prefs(requireContext())
        println("Faltas alumno y llega de "+origen)
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        if (origen == "ShowStudent" || origen =="recAlumnos") {
            binding.headerAsignaturaNombre.text="Asignatura"
            alumnoViewModel.getFaltasFromAlumno(prefs.getToken().toString())
        } else if (origen == "Cursos") {
            binding.headerAsignaturaNombre.text="Nombre"
            alumnoViewModel.getFaltasFromCurso(
                prefs.getData()!!.toInt(),
                prefs.getToken().toString()
            )
        }


        alumnoViewModel._alumno.observe(viewLifecycleOwner) { alumno ->
            alumno.faltas.let {
                if (alumno.faltas != null && (origen == "ShowStudent" || origen=="recAlumnos")){
                    initReciclerView(alumno.faltas!!, alumnoViewModel , userType)}
            }
            }


        if (origen == "Cursos") {
            alumnoViewModel._faltas.observe(viewLifecycleOwner) { faltas ->
                if (faltas != null) {
                    initReciclerView(faltas, alumnoViewModel, userType)
                }
            }
        }


//        val faltas = setOf(
//            Falta(1, 1, "Matemáticas","2024-05-01", 1, true),
//            Falta(2, 4, "Matemáticas 2º Eso", "2024-05-02", 1, false),
//            Falta(3, 2, "Geografía", "2024-05-03", 1, true),
//            Falta(4, 5, "Ciencias de la naturaleza", "2024-05-04", 1, false),
//            Falta(5, 3, "Lengua", "2024-05-05", 1, true),
//            Falta(6, 6, "Matemáticas", "2024-05-06", 1, false),
//            Falta(7, 2, "Historia", "2024-05-07", 1, true),
//            Falta(8, 3, "Matemáticas", "2024-05-10", 1, false),
//            Falta(9, 4, "Dibujo", "2024-05-15", 1, true),
//            Falta(10, 2, "Física", "2024-05-15", 1, false),
//            Falta(45, 3, "Matemáticas", "2024-05-21", 1, false),
//            Falta(43, 4, "Dibujo", "2024-05-20", 1, true),
//            Falta(21, 2, "Física", "2024-05-20", 1, false),
//            Falta(15, 4, "Dibujo", "2024-05-01", 1, true),
//            Falta(16, 2, "Física", "2024-05-01", 1, false),
//            Falta(17, 3, "Matemáticas", "2024-05-02", 1, false),
//            Falta(19, 4, "Dibujo", "2024-05-02", 1, true),
//            Falta(20, 2, "Física", "2024-05-02", 1, false)
//
//        )
//        alumno= Alumno(1, "Manuel", "Fernández", "", 1, faltas)
//
//
//        if(alumno.faltas?.isNotEmpty() == true)
//            initReciclerView(alumno.faltas!!)
//        else initReciclerView(null)

//        recyclerView.addOnItemTouchListener(
//            RecyclerViewItemClickListener(requireContext(), recyclerView, object : RecyclerViewItemClickListener.OnItemClickListener {
//                override fun onItemClick(view: View, position: Int) {
//
//                    val adapter = recyclerView.adapter as ShowFaltasAdapter
//                    adapter.removeFalta(position)
//                }
//            })
//        )
    }

    @SuppressLint("SetTextI18n")
    private fun initReciclerView(faltas: Set<Falta>?, viewModel: AlumnoViewModel, userType:String) {
        recyclerView = view?.findViewById(R.id.rvShowFaltas)!!
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        if (faltas.isNullOrEmpty()) {
            //binding.tvNoFaltas.text="No hay faltas disponibles"
            Toast.makeText(requireContext(), "No hay faltas disponibles", Toast.LENGTH_SHORT).show()
        } else {
            val faltasOrdenadas: LinkedHashSet<Falta> =
                faltas.sortedWith(compareByDescending<Falta> { it.fecha }.thenBy { it.hora })
                    .toSet() as LinkedHashSet<Falta>
            recyclerView.adapter = ShowFaltasAdapter(faltasOrdenadas, viewModel, userType)
        }
    }


//    @SuppressLint("NotifyDataSetChanged")
//    override fun onFaltaDelete(falta: Falta) {
//        val nuevasFaltas= alumno.faltas!!.toMutableSet()
//        nuevasFaltas.remove(falta)
//
//        recyclerView.adapter?.notifyDataSetChanged()
//        initReciclerView(nuevasFaltas)
//
//    }
}