package com.example.tycep_fe.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentShowStudentBinding
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel


class showStudent : Fragment() {

    private var _binding: FragmentShowStudentBinding?=null
    private val binding get() = _binding!!
    lateinit var alumnoViewModel: ViewModel
    lateinit var userViewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentShowStudentBinding.inflate(inflater, container, false)
        prefs = Prefs(requireContext())
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        val idAlumno:Int= prefs.getData()?.toInt()!!
        val token:String= prefs.getToken().toString()
        (alumnoViewModel as AlumnoViewModel).getAlumnoById(idAlumno, token)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        view.isFocusableInTouchMode = true
        view.requestFocus()


        (alumnoViewModel as AlumnoViewModel)._alumno.observe(viewLifecycleOwner) { alumno ->
            alumno?.let {
                _binding!!.tvShowStudentName.text= alumno.nombre
            }
        }


        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner){
                (alumnoViewModel as AlumnoViewModel)._alumno.observe(viewLifecycleOwner) { alumno ->
                    alumno?.let {
                        prefs.saveData(alumno.idCurso.toString())
                    }
                }
                }
                (userViewModel as UserViewModel)._tutorLegal.observe(viewLifecycleOwner){tutor ->
                    if(tutor.alumnos?.size!! ==1){

                        findNavController().navigate(R.id.action_showStudent_to_homeFragment)
                    }

                }
                return@setOnKeyListener false // Devuelve true para indicar que el evento ha sido consumido
            }
            return@setOnKeyListener false // Devuelve false para indicar que no has manejado el evento
        }
        _binding?.btnToAbscenses?.setOnClickListener{
            val token:String= prefs.getToken().toString()
            (alumnoViewModel as AlumnoViewModel).getFaltasFromAlumno(token)
            (alumnoViewModel as AlumnoViewModel)._alumno.observe(requireActivity()){ alumno ->
                alumno.let {
                    findNavController().navigate(R.id.faltasAlumno)
                }
            }
        }
    }
}