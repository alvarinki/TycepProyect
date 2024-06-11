package com.example.tycep_fe.fragments

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.Objects.ImagePicker_Uploader
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentShowStudentBinding
import com.example.tycep_fe.modelFB.ChatFB
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import com.squareup.picasso.Picasso


class showStudent : Fragment() {

    private var _binding: FragmentShowStudentBinding? = null
    private val binding get() = _binding!!
    lateinit var alumnoViewModel: AlumnoViewModel
    lateinit var userViewModel: UserViewModel
    private var alumnoId: Int = 0

    private var fotoAlumno: String = "Nula"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowStudentBinding.inflate(inflater, container, false)
        prefs = Prefs(requireContext())
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        val idAlumno: Int = prefs.getData()?.toInt()!!
        val token: String = prefs.getToken().toString()
        alumnoViewModel.getAlumnoById(idAlumno, token)
        binding.ibImageExchange.setOnClickListener {
            //Toast.makeText(requireContext(), "Dio mio", Toast.LENGTH_SHORT).show()
            //openImagePicker()
            ImagePicker_Uploader.pickAndUploadImage(this, 100)
        }

        userViewModel._profesor.observe(viewLifecycleOwner){
            binding.btnStartChat.visibility= View.VISIBLE
            binding.btnStartChat.isEnabled= true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alumnoViewModel = ViewModelProvider(requireActivity())[AlumnoViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        view.isFocusableInTouchMode = true
        view.requestFocus()


        alumnoViewModel._alumno.observe(viewLifecycleOwner) { alumno ->
            alumno?.let {
                println(alumno)
                _binding!!.tvShowStudentName.text = alumno.nombre + " "+alumno.apellidos
                alumnoId = alumno.id
                if (alumno.foto.length > 2) {
                    Picasso.get().load(alumno.foto).into(binding.ivStudent)
                    fotoAlumno = alumno.foto
                    println("Foto del alumno: " + fotoAlumno)
                }

            }
        }

        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                userViewModel._profesor.observe(viewLifecycleOwner) {
                    alumnoViewModel._alumno.observe(viewLifecycleOwner) { alumno ->
                        alumno?.let {
                            prefs.saveData(alumno.idCurso.toString())
                        }
                    }
                }
                userViewModel._tutorLegal.observe(viewLifecycleOwner) { tutor ->
                    if (tutor.alumnos?.size!! == 1) {
                        findNavController().navigate(R.id.action_showStudent_to_homeFragment)
                    }
                }
                return@setOnKeyListener false // Devuelve true para indicar que el evento ha sido consumido
            }
            return@setOnKeyListener false // Devuelve false para indicar que no has manejado el evento
        }
        _binding?.btnToAbscenses?.setOnClickListener {
            val action =
                showStudentDirections.actionShowStudentToFaltasAlumno(origen = "ShowStudent")
            findNavController().navigate(action)

        }

        binding.btnStartChat.setOnClickListener {
            alumnoViewModel._tutores.observe(viewLifecycleOwner) { tutores ->
                showPopupMenu(tutores + "Todos los tutores legales")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Llama a onActivityResult de ImageUploader desde tu fragmento

        println("Foto desde la bd: " + fotoAlumno)
        ImagePicker_Uploader.onActivityResult(
            requireContext(),
            requestCode,
            resultCode,
            data,
            userViewModel,
            "Student",
            alumnoId,
            prefs.getToken()!!,
            fotoAlumno
        ) { downloadUrl ->
            // Aquí puedes hacer cualquier cosa con la URL de descarga, como mostrarla en una ImageView
            println(downloadUrl)
            userViewModel._profesor.observe(viewLifecycleOwner) { profesor ->
//                println(alumnoId)
//                profesor.cursos?.filter { c -> c.alumnos.contains(c.alumnos.filter { a -> a.id==alumnoId }[0])}
                //?.get(0)?.alumnos!!.filter {a -> a.id == alumnoId}[0].foto= downloadUrl
//                println("Lo hace todo bien, foto: "+profesor.cursos?.filter { c -> c.alumnos.contains(c.alumnos.filter { a -> a.id==alumnoId }[0])}?.get(0)?.alumnos!!.filter {a -> a.id == alumnoId}.get(0).foto)
                val cursoConAlumno = profesor.cursos?.find { curso ->
                    curso.alumnos.any { alumno -> alumno.id == alumnoId }
                }

                // Encuentra el alumno dentro del curso encontrado
                val alumno = cursoConAlumno?.alumnos?.find { it.id == alumnoId }

                // Actualiza la foto del alumno si el alumno se encontró
                alumno?.let {
                    it.foto = downloadUrl
                    fotoAlumno = downloadUrl
                    println("Foto actualizada: ${it.foto}")
                } ?: run {
                    println("Alumno no encontrado.")
                }

            }
            Picasso.get().load(downloadUrl.toUri()).into(binding.ivStudent)
        }


    }

    private fun showPopupMenu(tutores: List<String>) {
        val popupMenu = PopupMenu(requireContext(), binding.btnStartChat)
        tutores.forEachIndexed { index, tutor ->
            popupMenu.menu.add(0, index, 0, tutor)
        }
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            val selectedTutor = menuItem.title.toString()
            var usuarioProfesor=" "
            var fotoProfesor= " "
            var nombreChat=" "

            userViewModel._profesor.observe(viewLifecycleOwner){profesor ->
                usuarioProfesor= profesor.usuario
                fotoProfesor=  profesor.foto
            }

            var usuarios: MutableMap<String, Boolean> = mutableMapOf()
            usuarios.put(usuarioProfesor, true)

            alumnoViewModel._alumno.observe(viewLifecycleOwner){alumno ->
                nombreChat= alumno.nombre + " "+alumno.apellidos + " con "
            }

            if(selectedTutor!="Todos los tutores legales"){
                var usuarioTutor=selectedTutor.split(" ").last()
                usuarios.put(usuarioTutor, true)
                nombreChat += usuarioTutor
            }
            else{
                tutores.forEach { tutor -> usuarios.put(tutor.split(" ").last(), true) }
                nombreChat += usuarioProfesor
            }
            val chatFB= ChatFB()
            chatFB.nombreChat=nombreChat
            chatFB.foto=fotoProfesor
            chatFB.usuarios= usuarios
            userViewModel.startChatWithTutors(chatFB)
            val action = showStudentDirections.actionShowStudentToHomeFragment(chatId = chatFB.id.toString(), origen="StartChat")
            findNavController().navigate(action)
            true
        }
        popupMenu.show()
    }

//    private fun openImagePicker() {
//        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//            Intent(MediaStore.ACTION_PICK_IMAGES).apply {
//                type = "image/*"
//            }
//        } else {
//            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        }
//        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImageUri = data.data
//            val storageReference = FirebaseStorage.getInstance().getReference("Images/${UUID.randomUUID()}")
//            storageReference.putFile(selectedImageUri!!)
//                .addOnSuccessListener { taskSnapshot ->
//                    // Obtener la URL de descarga
//                    storageReference.downloadUrl.addOnSuccessListener { uri ->
//                        val downloadUrl = uri.toString()
//                        // Guardar el enlace en la base de datos
//                        saveImageLinkToDatabase(downloadUrl)
//                        println("Url de descarga: "+downloadUrl)
//                        // Mostrar la imagen en ImageView
//                        binding.ivStudent.setImageURI(selectedImageUri)
//                    }.addOnFailureListener { exception ->
//                        // Manejar cualquier error
//                        Log.e("FirebaseStorage", "Error getting download URL", exception)
//                    }
//                }.addOnFailureListener { exception ->
//                    // Manejar cualquier error
//                    Log.e("FirebaseStorage", "Error uploading file", exception)
//                }
//
//            binding.ivStudent.setImageURI(selectedImageUri) // Corregir aquí
//        }
//    }
//
//    private fun saveImageLinkToDatabase(downloadUrl: String) {
//        val db = FirebaseFirestore.getInstance()
//        val imageInfo = hashMapOf(
//            "imageUrl" to downloadUrl
//            // Añade cualquier otra información que quieras guardar
//        )
//
//        db.collection("Images").add(imageInfo)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot added with ID: ${documentReference.id}" + "Path: " +documentReference.path)
//                val imageUrl = "${documentReference.parent.path}/${documentReference.id}" // Obtener la URL completa de la imagen
//                println(imageUrl)
//            }
//            .addOnFailureListener { e ->
//                Log.w("FirebaseFirestore", "Error adding document", e)
//                println("Falla???")
//            }
//    }
//
//
//
//
//    companion object {
//        private const val REQUEST_CODE_PERMISSION = 1001
//        private const val REQUEST_CODE_IMAGE_PICKER = 1002
//    }
}

//            findNavController().navigate(R.id.faltasAlumno)
//            val token:String= prefs.getToken().toString()
//            alumnoViewModel.getFaltasFromAlumno(token)
//            alumnoViewModel._alumno.observe(requireActivity()){ alumno ->
//                alumno.let {
//                    findNavController().navigate(R.id.faltasAlumno)
//                }
//            }