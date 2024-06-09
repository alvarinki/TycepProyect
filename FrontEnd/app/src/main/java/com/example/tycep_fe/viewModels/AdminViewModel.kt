package com.example.tycep_fe.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tycep_fe.models.AdminsUserData
import com.example.tycep_fe.repositories.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File

class AdminViewModel: ViewModel() {
     var mensaje= MutableLiveData<String>()

     val adminRepo = AdminRepository()

     fun registerProfesores(file: File, onSuccess: () -> Unit, onError: (String) -> Unit) {
          val requestBody = RequestBody.create("*/*".toMediaTypeOrNull(), file)
          val body = MultipartBody.Part.createFormData("file", file.name, requestBody)

          viewModelScope.launch {
               try {
                    val response = withContext(Dispatchers.IO) {
                         adminRepo.registerProfesores(body)
                    }
                    if (response.isSuccessful) {
                         onSuccess()
                    } else {
                         onError("Error al subir el archivo: ${response.message()}")
                    }
               } catch (e: Exception) {
                    onError("Error: ${e.message}")
               }
          }
     }

     fun uploadFile(file: MultipartBody.Part, type: String , token:String, callback: (String, List<AdminsUserData>?) -> Unit) {
          adminRepo.uploadFile(file, type, token) { response, adminsUserData ->
               callback(response, adminsUserData)
          }
     }
}