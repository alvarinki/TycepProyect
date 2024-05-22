package com.example.pruebafilepicker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var btnFilePicker: Button;
    private lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            btnFilePicker= findViewById(R.id.btnFilePicker)
            text= findViewById(R.id.textView)
            btnFilePicker.setOnClickListener{
                showFileChooser();
            }
            insets
        }
    }

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val uri: Uri? = data.data
                if (uri != null) {
                    val path = uri.path
                    val file = File(path!!)
                    displayFilePath(file)
                }
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            filePickerLauncher.launch(Intent.createChooser(intent, "Selecciona un archivo"))
        } catch (exception: Exception) {
            Toast.makeText(this, "Por favor instala un gestor de archivos", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun displayFilePath(file: File) {
        val path = file.path
        val fileName = file.name

        text.text= "$fileName $path"

    }
}