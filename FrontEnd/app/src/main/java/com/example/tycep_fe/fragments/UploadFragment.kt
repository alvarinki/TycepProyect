package com.example.tycep_fe.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.FragmentUploadBinding
import com.example.tycep_fe.models.AdminsUserData
import com.example.tycep_fe.viewModels.AdminViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalTime

class UploadFragment : Fragment() {

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

        val datosAinsertar = resources.getStringArray(R.array.datos_a_insertar)

        _binding!!.dataTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Actualizar el TextView con el texto correspondiente basado en la posición del Spinner
                    _binding!!.formatInfoText.text = datosAinsertar[position]
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
                    val token = "eyJraWQiOiJ5bm55Z3Q4OXI1Znk5aXV5OTRqZnVleTNoZmtmZHM0bGFza2RmbGFzZGtmMHc5cml3MHA5aXJlb2siLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMyIsImlhdCI6MTcxNzkxMjY2Mywic3ViIjoicnJvXzgwIiwiaXNzIjoiTWFpbiIsImV4cCI6MTcxODUxNzQ2M30.ZMyZy31mvNe1AAL7Sea_hOve20SH6P6_pE8DgSSedLw"

                    viewModel.uploadFile(filePart, insertType, token) { response, adminsUserData ->
                        if (response.isNotEmpty()) {
                            Toast.makeText(requireContext(), response, Toast.LENGTH_LONG).show()
                            println(response)
                        }
                        adminsUserData?.let {
                            if(adminsUserData.isNotEmpty()){
                                insertType += formattedMinutes+formattedSeconds
                                saveUserDataToFile(requireContext(), insertType, it)
                                Toast.makeText(requireContext(), "Archivo guardado correctamente", Toast.LENGTH_LONG).show()
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

                return "Todos los datos ya están en la base de datos"

            val fileName = "${dataType}${LocalTime.now().hour}_${LocalTime.now().minute}_${LocalTime.now().second}.txt"
            val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
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
