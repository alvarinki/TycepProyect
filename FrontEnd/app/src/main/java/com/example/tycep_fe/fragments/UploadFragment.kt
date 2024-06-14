package com.example.tycep_fe.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentUploadBinding
import com.example.tycep_fe.models.AdminsUserData
import com.example.tycep_fe.viewModels.AdminViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalTime

class UploadFragment : Fragment() {
    private lateinit var  userViewModel: UserViewModel
    private lateinit var viewModel: AdminViewModel
    private val FILE_SELECT_CODE = 0
    private val REQUEST_CODE = 1
    private var _binding: FragmentUploadBinding? = null
    private var insertType:String=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentUploadBinding.inflate(inflater, container, false)
        val view =_binding!!.root
        return view
    }
        // Inflate the layout for this fragment


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)

        view.findViewById<ImageButton>(R.id.ibUpload).setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(
                Intent.createChooser(intent, "Escoje un archivo"),
                FILE_SELECT_CODE
            )
        }

        val spinner = _binding!!.dataTypeSpinner
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.datos_a_insertar,
            R.layout.selected_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_item)
        spinner.adapter = adapter

//        view.setOnKeyListener { _, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
//                val prefs= Prefs(requireContext())
//                prefs.clearToken()
//                ActivityCompat.finishAffinity(this.requireActivity())
//                return@setOnKeyListener false // Devuelve true para indicar que el evento ha sido consumido
//            }
//            return@setOnKeyListener false // Devuelve false para indicar que no has manejado el evento
//        }
            _binding!!.btnLogout.setOnClickListener{
                val prefs= Prefs(requireContext())
                prefs.clearToken()
                ActivityCompat.finishAffinity(this.requireActivity())
            }


        val instrucciones_inserts = resources.getStringArray(R.array.instrucciones_para_inserts)

        _binding!!.dataTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Actualizar el TextView con el texto correspondiente basado en la posición del Spinner
                    _binding!!.formatInfoText.text = instrucciones_inserts[position]
                    insertType = parent?.getItemAtPosition(position).toString()


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val file = getFileFromUri(requireContext(), uri)
                if (file != null) {
                    val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), file)
                    val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)
                      // O el tipo que necesites enviar
                    val (minutes, seconds) = getCurrentMinutesAndSeconds()
                    val formattedMinutes = formatToTwoDigits(minutes)
                    val formattedSeconds = formatToTwoDigits(seconds)
                    val prefs= Prefs(requireContext())
                    val token = prefs.getToken()!!
                    println(token)

                    viewModel.uploadFile(filePart, insertType, token) { response, adminsUserData ->
                        if (response.isNotEmpty()) {
                            Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                            println(response)
                        }
                        adminsUserData?.let {
                            if(adminsUserData.isNotEmpty()){
                                insertType += formattedMinutes+formattedSeconds
                                val mensaje:String= saveUserDataToFile(requireContext(), insertType, it)
                                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
                            }
                            else Toast.makeText(requireContext(), "Todos los datos ya se encontraban en la base de datos", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener el archivo", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val fileName = getFileName(context, uri)
        val tempFile = File(context.cacheDir, fileName)
        tempFile.outputStream().use { outputStream ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return tempFile
    }

    private fun getFileName(context: Context, uri: Uri): String {
        var name = ""
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        returnCursor?.use {
            val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            name = it.getString(nameIndex)
        }
        return name
    }

    private fun saveUserDataToFile(context: Context, dataType: String, adminsUserData: List<AdminsUserData>): String {
        return try {

            if (adminsUserData.isEmpty()) {
                return "Todos los datos ya están en la base de datos"
            }

            val fileName = "${dataType}${LocalTime.now().hour}_${LocalTime.now().minute}_${LocalTime.now().second}.txt"
            val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            if (directory != null && !directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, fileName)
            BufferedWriter(FileWriter(file)).use { writer ->
                for (userData in adminsUserData) {
                    writer.write("${userData.username};${userData.password};${userData.nombreApellidos};${userData.dni}")
                    writer.newLine()
                }

            return "Archivo con datos de usuarios registrados guardado correctamente: ${file.absolutePath}"
            }
        } catch (e: IOException) {
            return "Error al guardar el archivo: ${e.message}"
        }
    }

    fun formatToTwoDigits(value: Int): String {
        return String.format("%02d", value)
    }

    fun getCurrentMinutesAndSeconds(): Pair<Int, Int> {
        val now = LocalTime.now()
        val minutes = now.minute
        val seconds = now.second
        return Pair(minutes, seconds)
    }
}
