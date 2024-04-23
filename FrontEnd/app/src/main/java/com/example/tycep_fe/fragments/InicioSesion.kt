package com.example.tycep_fe.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentInicioSesionBinding
import com.example.tycep_fe.viewModels.UserViewModel

class InicioSesion : Fragment() {

    private var _binding: FragmentInicioSesionBinding?=null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findNavController().navigate(R.id.action_inicioSesion_to_principal)
        // Inflate the layout for this fragment
        _binding= FragmentInicioSesionBinding.inflate(inflater, container, false)

        binding.btnSignIn.setOnClickListener{
                val loginRequestDto= LoginRequestDto(binding.editTextUsername.text.toString(), binding.editTextPassword.text.toString())
                val userType:String= userViewModel.userLogin(loginRequestDto)
                if(userType== "Profesor" || userType==" Tutor legal"){
                    findNavController().navigate(R.id.action_inicioSesion_to_principal)

//                    userViewModel._profesor.observe(viewLifecycleOwner) { profesor ->
//                        // profesor se ha actualizado, navegar al nuevo Fragmento
//
//                    }

                }




        }
//                       val response: LoginResponseDto?= loginResponse.body()
//                        if(response?.userType == "Profesor"){
//                            Toast.makeText(requireContext(), "Es profesor", Toast.LENGTH_SHORT)
//                                .show()
//                            userViewModel.tutorLegal.postValue(response.userData as TutorLegal?)
//
//                        }
//                        else if(response?.userType== "TutorLegal"){
//                            Toast.makeText(requireContext(), "Es tutor legal/ padre", Toast.LENGTH_SHORT)
//                                .show()
//                            userViewModel.tutorLegal.postValue(response.userData as TutorLegal?)
//                            findNavController().navigate(R.id.action_inicioSesion_to_principal)
//                        }
        return binding.root
    }

}