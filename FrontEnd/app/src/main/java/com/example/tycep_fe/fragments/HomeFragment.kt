package com.example.tycep_fe.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.Objects.ImagePicker_Uploader
import com.example.tycep_fe.R
import com.example.tycep_fe.adapter.ChatAdapter
import com.example.tycep_fe.databinding.FragmentHomeBinding
import com.example.tycep_fe.databinding.NavHeaderPrincipalBinding
import com.example.tycep_fe.modelFB.ChatFB
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import com.squareup.picasso.Picasso
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var alumnoViewModel: AlumnoViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var navHeaderBinding: NavHeaderPrincipalBinding
    private lateinit var menuItemChange: MenuItem
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private var backPressed = 0
    private var fotoProfesor="Nula"
    private var idProfesor:Int=0
    private val database = FirebaseDatabase.getInstance()
    val args: HomeFragmentArgs by navArgs()
    private val binding get() = _binding!!

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        recyclerView = requireView().findViewById(R.id.recyclerChats)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        chatAdapter = ChatAdapter(requireContext(), emptyList<ChatFB>().toMutableList()) // Adapter inicialmente vacío
//        recyclerView.adapter = chatAdapter

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        recyclerView = root.findViewById(R.id.recyclerChats)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = ChatAdapter("", emptyList<ChatFB>().toMutableList()) // Adapter inicialmente vacío
        recyclerView.adapter = chatAdapter
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Líneas para pruebas
        //findNavController().navigate(R.id.action_homeFragment_to_chat)
        //findNavController().navigate(R.id.action_homeFragment_to_pselectHorario)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        userViewModel .token.observe(viewLifecycleOwner) { token ->
            prefs = Prefs(requireContext())
            prefs.saveToken(token)
        }


//        val mensajesChat1 = setOf(
//            Mensaje(1, 1, "Hola", "2024-04-23", "Usuario1"),
//            Mensaje(2, 1, "¿Cómo estás?", "2024-04-23", "Usuario1"),
//            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
//            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario1")
//        )
//
//        val mensajesChat2 = setOf(
//            Mensaje(3, 2, "¡Hola a todos!", "2024-04-22", "Usuario3"),
//            Mensaje(4, 2, "¿Qué tal?", "2024-04-22", "Usuario4")
//        )
//
//        val mensajesChat3 = setOf(
//            Mensaje(5, 3, "Buenos días", "2024-04-21", "Usuario5"),
//            Mensaje(6, 3, "¿Cómo va todo?", "2024-04-21", "Usuario6")
//        )
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
//        val chats = setOf(
//            Chat(1, "Chat1", true, mensajesChat1),
//            Chat(2, "Chat2", false, mensajesChat2),
//            Chat(3, "Chat3", true, mensajesChat3)
//
//        )


        //Mensajes de pruebas
        //initReciclerView(chats)
        menuItemChange = binding.navView.menu.findItem(R.id.nav_studentdata_or_course)

        userViewModel ._profesor.observe(viewLifecycleOwner) { profesor ->
            obtenerChatsDeUsuario(profesor.usuario)
//            val origen:String= args.origen
//
//            when (origen) {
//                "StartChat" -> {
//                    val chatId:String= args.chatId
//                    println(chatId)
//                    val action = HomeFragmentDirections.actionHomeFragmentToChatDetail(chatId = chatId, nombreUsuario = profesor.usuario)
//                    findNavController().navigate(action)
//                }
//            }

            navHeaderBinding.tvName.text= "${profesor.nombre} ${profesor.apellidos}"
            navHeaderBinding.tvUsername.text= profesor.usuario
            if(profesor.foto.length>2){
                fotoProfesor=profesor.foto
                Picasso.get().load(profesor.foto).fit().centerCrop().into(navHeaderBinding.imageView)
            }
            idProfesor=profesor.id

            navHeaderBinding.ibImageExchange.setOnClickListener{
                ImagePicker_Uploader.pickAndUploadImage(this, 100)
            }

            menuItemChange.setTitle("Cursos")
            binding.navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_studentdata_or_course -> {
                        if (profesor.cursos==null) {
                            userViewModel .getCursosFromProfesor()
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
                        if (profesor.cursos==null) {
                            userViewModel .getCursosFromProfesor()
                        }

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
        }
//            profesor?.let {
//                // El profesor está disponible, navega al nuevo Fragmento
//
//                //initReciclerView(profesor.chats!!)
//            } ?: run {
//                // Manejar el caso en el que profesor es nulo
//            }


        userViewModel ._tutorLegal.observe(viewLifecycleOwner) { tutorLegal ->
            obtenerChatsDeUsuario(tutorLegal.usuario)
            menuItemChange.setTitle("Alumnos")
            binding.navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_studentdata_or_course -> {
                        //userViewModel 
                        //findNavController().navigate(R.id.action_homeFragment_to_recyclerAlumnos)
                        val action = HomeFragmentDirections.actionHomeFragmentToRecyclerAlumnos(origen = "Cursos")
                        findNavController().navigate(action)
                        true
                    }

                    R.id.nav_schedule -> {

                        val action = HomeFragmentDirections.actionHomeFragmentToRecyclerAlumnos(origen = "HomeH")
                        findNavController().navigate(action)
                        true
//                        val origen="Home"
//                        val fragment = Alumnos.newInstance(origen)
//                        parentFragmentManager.commit {
//                            replace(androidx.navigation.fragment.R.id.nav_host_fragment_container, fragment)
//                            addToBackStack(null)
//                        }
                    }

                    R.id.nav_absences -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToRecyclerAlumnos(origen = "HomeA")
                        findNavController().navigate(action)
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
        }
//            tutorLegal?.let {
//                // El profesor está disponible, navega al nuevo Fragmento
//
//                //initReciclerView(tutorLegal.chats!!)
//            } ?: run {
//                // Manejar el caso en el que profesor es nulo
//            }


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
                    }, 2000)
                } else {
                    finishAffinity(requireActivity())


                    val timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            backPressed = 0
                        }
                    }, 2000.toLong())
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

//    private fun initReciclerView(nombreUsuario:String,chats: MutableList<ChatFB>) {
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerChats)
//        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
//        recyclerView?.adapter = ChatAdapter(nombreUsuario, chats)
//
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Llama a onActivityResult de ImageUploader desde tu fragmento


        ImagePicker_Uploader.onActivityResult(requireContext(), requestCode, resultCode, data, userViewModel, "Profesor", idProfesor, prefs.getToken()!!, fotoProfesor)
        { downloadUrl ->
            // Aquí puedes hacer cualquier cosa con la URL de descarga, como mostrarla en una ImageView
            println(downloadUrl)
            userViewModel._profesor.observe(viewLifecycleOwner){profesor ->


                profesor?.let {
                    if (profesor.foto.length > 2) {
                        it.foto = downloadUrl
                        fotoProfesor = downloadUrl
                        println("Foto profesor: "+fotoProfesor)
                    }


                } ?: run {
                    println("Problemita con foto profe.")
                }


            }
            Picasso.get().load(downloadUrl.toUri()).fit().centerCrop().into(navHeaderBinding.imageView)
        }
    }

    fun obtenerChatsDeUsuario(nombreUsuario: String) {
        val usuariosRef = database.getReference("Usuarios")

        // Primero, busca el ID del usuario usando su nombre de usuario
        usuariosRef.orderByChild("username").equalTo(nombreUsuario)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (usuarioSnapshot in dataSnapshot.children) {

                        // Obtenemos el ID del usuario
                        val usuarioId = usuarioSnapshot.key

                        // Ahora, con el ID del usuario, buscamos sus chats en la base de datos
                        if (usuarioId != null) {
                            val chatsRef = database.getReference("Chat")
                            chatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(chatsDataSnapshot: DataSnapshot) {
                                    val chatsList = mutableListOf<ChatFB>()
                                    for (chatSnapshot in chatsDataSnapshot.children) {
                                        val usuarios = chatSnapshot.child("usuarios")
                                        if (usuarios.hasChild(usuarioSnapshot.child("username").getValue(String::class.java).toString())) {
                                            val chatFB = chatSnapshot.getValue(ChatFB::class.java)

                                            // Por ejemplo, imprimir el nombre del chatFB
                                            chatFB?.let {
                                                val chatId = chatSnapshot.key
                                                // Asignar el ID del chatFB al atributo "id" del objeto chatFB
                                                it.id = chatId!!
                                                chatsList.add(it) }
                                        }
                                    }
                                    chatAdapter.updateData(nombreUsuario, chatsList)
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Manejo de errores
                                }
                            })
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejo de errores
                }
            })
    }
}