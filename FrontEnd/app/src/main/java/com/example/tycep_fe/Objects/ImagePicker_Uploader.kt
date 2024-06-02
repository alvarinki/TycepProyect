package com.example.tycep_fe.Objects

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tycep_fe.Dtos.PhotoRequest
import com.example.tycep_fe.viewModels.AlumnoViewModel
import com.example.tycep_fe.viewModels.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

object ImagePicker_Uploader {
    private const val REQUEST_CODE_IMAGE_PICKER = 100

    fun pickAndUploadImage(fragment: Fragment, requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        fragment.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?, userViewModel: UserViewModel, tipo: String, requiredId:Int, token:String, imagenAnterior:String,  onSuccess: (downloadUrl: String) -> Unit) {
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            if(imagenAnterior!="Nula"){
                FirebaseStorage.getInstance().getReferenceFromUrl(imagenAnterior).delete().addOnSuccessListener {
                    Toast.makeText(context , "Eliminose", Toast.LENGTH_SHORT).show()
                }
            }

            val selectedImageUri = data.data
            val storageReference = FirebaseStorage.getInstance().getReference("Images/${UUID.randomUUID()}")
            storageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        println(downloadUrl)
                        saveImageLinkToDatabase(downloadUrl)
                        //val envio= "$tipo $requiredId $downloadUrl"
                        userViewModel.postPhoto( PhotoRequest(downloadUrl, requiredId, tipo), token)
                        onSuccess(downloadUrl)
                    }.addOnFailureListener { exception ->
                        Log.e("FirebaseStorage", "Error getting download URL", exception)
                    }
                }.addOnFailureListener { exception ->
                    Log.e("FirebaseStorage", "Error uploading file", exception)
                }
        }
    }

    private fun saveImageLinkToDatabase(downloadUrl: String) {
        val db = FirebaseFirestore.getInstance()
        val imageInfo = hashMapOf(
            "imageUrl" to downloadUrl
            // Añade cualquier otra información que quieras guardar
        )

        db.collection("Images").add(imageInfo)
            .addOnSuccessListener { documentReference ->
                Log.d("FirebaseFirestore", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseFirestore", "Error adding document", e)
            }
    }
}