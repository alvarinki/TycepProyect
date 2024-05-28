package com.example.tycep_fe.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.ChatAdapter
import com.example.tycep_fe.databinding.FragmentHomeBinding
import com.example.tycep_fe.databinding.NavHeaderPrincipalBinding
import com.example.tycep_fe.models.Chat
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {
    private lateinit var userViewModel: ViewModel
    private lateinit var alumnoViewModel: ViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var navHeaderBinding: NavHeaderPrincipalBinding
    private lateinit var menuItemChange: MenuItem
    private lateinit var bundle: Bundle
    private var backPressed = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }
        navHeaderBinding = NavHeaderPrincipalBinding.bind(binding.navView.getHeaderView(0))
        navHeaderBinding.tvName.text = "hola"
        navHeaderBinding.tvUsername.text = "adios"




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Líneas para pruebas
        //findNavController().navigate(R.id.action_homeFragment_to_chat)
        //findNavController().navigate(R.id.action_homeFragment_to_pselectHorario)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        (userViewModel as UserViewModel).token.observe(viewLifecycleOwner) { token ->
            prefs = Prefs(requireContext())
            prefs.saveToken(token)
        }


        val mensajesChat1 = setOf(
            Mensaje(1, 1, "Hola", "2024-04-23", "Usuario1"),
            Mensaje(2, 1, "¿Cómo estás?", "2024-04-23", "Usuario1"),
            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario1")
        )

        val mensajesChat2 = setOf(
            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario4")
        )

        val mensajesChat3 = setOf(
            Mensaje(5, 3, "Buenos días", "2024-04-21", "Usuario5"),
            Mensaje(6, 3, "¿Cómo va todo?", "2024-04-21", "Usuario6")
        )
        //Código momentáneo para pasar entre fragments
//        binding.navView.setNavigationItemSelectedListener { menuItem ->
//            when(menuItem.itemId){
//                R.id.nav_studentdata_or_course->{
//
//                    findNavController().navigate(R.id.action_homeFragment_to_cursos)
//                    true
//                }
//                R.id.nav_schedule->{
//                    findNavController().navigate(R.id.action_homeFragment_to_horario)
//                    true
//                }
//                R.id.nav_absences->{
//                    findNavController().navigate(R.id.action_homeFragment_to_pselectHorario)
//                    true
//                }
//                R.id.nav_configuration->{
//                    true
//                }
//                R.id.nav_exit ->{
//                    prefs = Prefs(requireContext())
//                    prefs.clearToken()
//                    finishAffinity(this.requireActivity())
//                    true
//                }
//
//                else -> false
//            }
//        }
        // Crear la lista de chats
        val chats = setOf(
            Chat(1, "Chat1", true, mensajesChat1),
            Chat(2, "Chat2", false, mensajesChat2),
            Chat(3, "Chat3", true, mensajesChat3)

        )


        //Mensajes de pruebas
        //initReciclerView(chats)
        menuItemChange = binding.navView.menu.findItem(R.id.nav_studentdata_or_course)

        (userViewModel as UserViewModel)._profesor.observe(viewLifecycleOwner) { profesor ->

            menuItemChange.setTitle("Cursos")
            binding.navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_studentdata_or_course -> {
                        if (profesor.cursos!!.isEmpty()) {
                            (userViewModel as UserViewModel).getCursosFromProfesor()
                        }
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToCursos(origen = "Home")
                        findNavController().navigate(action)
                        //findNavController().navigate(R.id.action_homeFragment_to_cursos)
                        true
                    }

                    R.id.nav_schedule -> {
                        (alumnoViewModel as AlumnoViewModel)._alumno.postValue(null)
                        findNavController().navigate(R.id.action_homeFragment_to_horario)
                        true
                    }

                    R.id.nav_absences -> {
                        findNavController().navigate(R.id.action_homeFragment_to_seleccionFaltas)
                        true
                    }

                    R.id.nav_configuration -> {
                        true
                    }

                    R.id.nav_exit -> {
                        prefs = Prefs(requireContext())
                        prefs.clearToken()
                        finishAffinity(this.requireActivity())
                        true
                    }

                    else -> false
                }
            }
            profesor?.let {
                // El profesor está disponible, navega al nuevo Fragmento

                initReciclerView(profesor.chats!!)
            } ?: run {
                // Manejar el caso en el que profesor es nulo
            }
        }

        (userViewModel as UserViewModel)._tutorLegal.observe(viewLifecycleOwner) { tutorLegal ->
            menuItemChange.setTitle("Alumnos")

            binding.navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_studentdata_or_course -> {
                        //(userViewModel as UserViewModel)
                        //findNavController().navigate(R.id.action_homeFragment_to_recyclerAlumnos)
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToRecyclerAlumnos(origen = "Cursos")
                        findNavController().navigate(action)
                        true
                    }

                    R.id.nav_schedule -> {

//                        val origen="Home"
//                        val fragment = Alumnos.newInstance(origen)
//                        parentFragmentManager.commit {
//                            replace(androidx.navigation.fragment.R.id.nav_host_fragment_container, fragment)
//                            addToBackStack(null)
//                        }
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToRecyclerAlumnos(origen = "Home")
                        findNavController().navigate(action)
                        true
                    }

                    R.id.nav_absences -> {

                        true
                    }

                    R.id.nav_configuration -> {
                        true
                    }

                    R.id.nav_exit -> {
                        prefs = Prefs(requireContext())
                        prefs.clearToken()
                        finishAffinity(this.requireActivity())
                        true
                    }

                    else -> false
                }
            }

            tutorLegal?.let {
                // El profesor está disponible, navega al nuevo Fragmento

                initReciclerView(tutorLegal.chats!!)
            } ?: run {
                // Manejar el caso en el que profesor es nulo
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressed == 0) {
                    Toast.makeText(
                        requireContext(),
                        "Clique de nuevo para salir de la aplicación",
                        Toast.LENGTH_SHORT
                    ).show()
                    backPressed++
                    Handler(Looper.getMainLooper()).postDelayed({
                        backPressed = 0
                        println("Entra aqui")
                    }, 2000)
                } else {
                    finishAffinity(requireActivity())


                    val timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            backPressed = 0
                            println("Entra aqui")
                        }
                    }, 2000.toLong())
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

    private fun initReciclerView(chats: Set<Chat>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerChats)
        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = ChatAdapter(chats, requireContext())

    }


}