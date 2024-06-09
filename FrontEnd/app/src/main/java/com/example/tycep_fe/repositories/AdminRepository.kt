package com.example.tycep_fe.repositories

import com.example.tycep_fe.models.AdminsUserData
import com.example.tycep_fe.services.AdminApi
import com.example.tycep_fe.services.AdminApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRepository {
    suspend fun registerProfesores(file: MultipartBody.Part): Response<String> = AdminApi.retrofitService.registerProfesores(file)

    fun uploadFile(file: MultipartBody.Part, type: String, token:String, callback: (String, List<AdminsUserData>?) -> Unit) {
        val typeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), type)
        AdminApi.retrofitService.uploadFile(file, typeRequestBody, token).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string() ?: ""
                    if (responseBody.startsWith("[")) {
                        val adminsUserData = parseAdminsUserData(responseBody)
                        println(adminsUserData)
                        callback("", adminsUserData)
                    } else {
                        callback(responseBody, null)
                    }
                } else {
                    callback("Upload failed", null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback("Upload error: ${t.message}", null)
            }
        })
    }

    private fun parseAdminsUserData(responseBody: String): List<AdminsUserData> {
        // Implement the logic to parse the JSON string into a list of AdminsUserData
        val gson = Gson()
        return gson.fromJson(responseBody, Array<AdminsUserData>::class.java).toList()
    }
}