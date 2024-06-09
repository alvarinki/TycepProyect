package com.example.tycep_fe.services

import com.example.tycep_fe.models.Alumno
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.StringTokenizer

private const val urlBase = "http://192.168.56.1:8080/admin/"

private val retrofit= Retrofit.Builder().baseUrl(urlBase).addConverterFactory(GsonConverterFactory.create()).build()

interface AdminApiService{

    @POST("registerProfesores")
    suspend fun registerProfesores(@Part file: MultipartBody.Part): Response<String>

    @Multipart
    @POST("upload")
    fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("type") type:RequestBody,
        @Header("authorization") token: String): Call<ResponseBody>

}

object AdminApi{
    val retrofitService: AdminApiService by lazy { retrofit.create(AdminApiService::class.java) }
}