package com.example.tycep_fe.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentInicioSesionBinding
import com.example.tycep_fe.viewModels.UserViewModel
import com.google.firebase.firestore.auth.User
import java.util.Timer
import java.util.TimerTask

class InicioSesion : Fragment() {

    private var _binding: FragmentInicioSesionBinding? = null
    private val binding get() = _binding!!
    lateinit var userViewModel: UserViewModel


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val prefs = Prefs(requireContext())
        _binding = FragmentInicioSesionBinding.inflate(inflater, container, false)
        //Linea para pruebas
        prefs.clearToken()
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
            if (prefs.getToken()?.length!! > 4) {
                val loginRequestDto = LoginRequestDto("", "", prefs.getToken().toString())
//
                userViewModel.userLogin(loginRequestDto)
                userViewModel.token.observe(viewLifecycleOwner) {
                    println("Token: " + prefs.getToken().toString())
                    findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)
                }


//            userViewModel._profesor.observe(requireActivity()){profesor ->
//
//            }
        }
        // Inflate the layout for this fragment


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Línea para adelantar mientras arreglo carga de datos
        //findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)

        //userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding.btnSignIn.setOnClickListener {
            val loginRequestDto = LoginRequestDto(
                binding.editTextUsername.text.toString(),
                binding.editTextPassword.text.toString(),
                ""
            )
            userViewModel.userLogin(loginRequestDto)
            //println("Token pre prefs "+token)
            prefs = Prefs(requireContext())
            if(_binding!!.checkBoxAdmin.isChecked){
                userViewModel._admin.observe(viewLifecycleOwner){
                    userViewModel.token.observe(viewLifecycleOwner){token->
                        prefs.saveToken(token)
                        findNavController().navigate(R.id.action_inicioSesion_to_uploadFragment)
                    }
                }
            }
            //prefs.clearToken()
            //prefs.saveToken(token)
            //findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)
//
//            userViewModel._profesor.observe(requireActivity()){
//                findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)
//                Toast.makeText(requireContext(), "AVISO: Se mantendrá su sesión iniciada hasta que la cierre", Toast.LENGTH_SHORT).show()
//            }
//
//            userViewModel._tutorLegal.observe(requireActivity()){
//
//                findNavController().navigate(R.id.action_inicioSesion_to_homeFragment)
//                Toast.makeText(requireContext(), "AVISO: Se mantendrá su sesión iniciada hasta que la cierre", Toast.LENGTH_SHORT).show()
//            }

            //if(userType== "Profesor" || userType==" Tutor legal"){


//                userViewModel._profesor.observe(viewLifecycleOwner) { profesor ->
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