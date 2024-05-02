package com.example.tycep_fe.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentInicioSesionBinding
import com.example.tycep_fe.viewModels.UserViewModel

class InicioSesion : Fragment() {

    private var _binding: FragmentInicioSesionBinding?=null
    private val binding get() = _binding!!
    lateinit var userViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding= FragmentInicioSesionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Línea para adelantar mientras arreglo carga de datos
        //findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)

        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.btnSignIn.setOnClickListener{
            val loginRequestDto= LoginRequestDto(binding.editTextUsername.text.toString(), binding.editTextPassword.text.toString())
            (userViewModel as UserViewModel).userLogin(loginRequestDto)
            //println("Token pre prefs "+token)
            prefs= Prefs(requireContext())
            prefs.clearToken()
            //prefs.saveToken(token)

            findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)



            //if(userType== "Profesor" || userType==" Tutor legal"){


//                (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) { profesor ->
//                    // profesor se ha actualizado, navegar al nuevo Fragmento
//                    profesor?.let {
//                        // El profesor está disponible, navega al nuevo Fragmento
//                        println("Profesor cargado: "+ profesor)
//                        findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)
//                    } ?: run {
//                        // Manejar el caso en el que profesor es nulo
//                    }
//
//                }


        }
    }

}