package com.example.tycep_fe.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.Dtos.LoginResponseDto
import com.example.tycep_fe.models.Profesor
import com.example.tycep_fe.models.TutorLegal
import com.example.tycep_fe.models.Usuario
import com.example.tycep_fe.repositories.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.logging.Handler

class UserViewModel: ViewModel() {

    private val repository= UserRepository()

    val admin= MutableLiveData<Usuario>()
    val tutorLegal= MutableLiveData<TutorLegal>()
    val _profesor= MutableLiveData<Profesor>()

    val profesor: LiveData<Profesor>
        get() = _profesor
    fun userLogin(loginRequestDto: LoginRequestDto): String{
        var typeUser: String="client"
        viewModelScope.launch {
            val response:Response<LoginResponseDto> =repository.userLogin(loginRequestDto)
            if(response.isSuccessful){
                typeUser= response.body()?.userType.toString()
                if(response.body()?.userType== "Profesor"){
                val user= response.body()?.userData
                println(Gson().toJson(user))
                val profe:Profesor= Gson().fromJson(Gson().toJson(user), Profesor::class.java)
                println("Profesor casteado: $profe")
                _profesor.postValue(profe)

                }
                else if(response.body()?.userType== "Tutor Legal"){
                    val user= response.body()?.userData
                    println(user).toString()
                    println(Gson().toJson(user))
                    val tutor: TutorLegal=  Gson().fromJson(Gson().toJson(user), TutorLegal::class.java)
                    tutorLegal.postValue(tutor)
                }
                else typeUser="admin"
            }

        }
        return typeUser
    }
}