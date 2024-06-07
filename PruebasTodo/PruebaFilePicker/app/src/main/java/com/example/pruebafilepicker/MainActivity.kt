package com.example.pruebafilepicker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // Mostrar el Toast aquí
        Toast.makeText(applicationContext, "Hora de mostrar el Toast!", Toast.LENGTH_LONG).show()
        return Result.success()
    }
}

// Programar el WorkManager
fun scheduleAlarmWorker(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<AlarmWorker>(7, TimeUnit.DAYS)
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}

// Calcular el retraso inicial hasta la próxima ocurrencia del lunes a las 10 AM
fun calculateInitialDelay(): Long {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        set(Calendar.HOUR_OF_DAY, 10)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    val now = Calendar.getInstance()
    if (calendar.timeInMillis < now.timeInMillis) {
        calendar.add(Calendar.WEEK_OF_YEAR, 1)
    }
    return calendar.timeInMillis - now.timeInMillis
}

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