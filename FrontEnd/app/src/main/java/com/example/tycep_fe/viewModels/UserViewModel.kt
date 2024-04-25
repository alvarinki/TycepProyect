package com.example.tycep_fe.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerrecorridos.preferences.Prefs
import com.example.recyclerrecorridos.preferences.TokenUsuarioApplication.Companion.prefs
import com.example.tycep_fe.Dtos.LoginRequestDto
import com.example.tycep_fe.Dtos.LoginResponseDto
import com.example.tycep_fe.models.Curso
import com.example.tycep_fe.models.Mensaje
import com.example.tycep_fe.models.Profesor
import com.example.tycep_fe.models.TutorLegal
import com.example.tycep_fe.models.Usuario
import com.example.tycep_fe.repositories.ProfesorRepository
import com.example.tycep_fe.repositories.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.logging.Handler

class UserViewModel(): ViewModel() {

    private val userRepo= UserRepository()
    private val profesorRepo= ProfesorRepository()
    val admin= MutableLiveData<Usuario>()
    val _tutorLegal= MutableLiveData<TutorLegal>()
    var _profesor= MutableLiveData<Profesor>()

    val profesor: LiveData<Profesor>
        get() = _profesor

    fun setProfesorValue(profesor: Profesor){
        _profesor.postValue(profesor)
    }
    val tutorLegal: LiveData<TutorLegal>
        get() = _tutorLegal


    fun userLogin(loginRequestDto: LoginRequestDto): String{
        var token=""
        var typeUser=""
        viewModelScope.launch {
            val response:Response<LoginResponseDto> =userRepo.userLogin(loginRequestDto)
            if(response.isSuccessful){
                token= response.body()?.token.toString()
                typeUser= response.body()?.userType.toString()
                if(response.body()?.userType== "Profesor"){
                val user= response.body()?.userData

                val profe:Profesor= Gson().fromJson(Gson().toJson(user), Profesor::class.java)
                println("Profesor casteado: $profe")
                _profesor.postValue(profe)

                }
                else if(response.body()?.userType== "Tutor Legal"){
                    val user= response.body()?.userData
                    println(user).toString()
                    println(Gson().toJson(user))
                    val tutor: TutorLegal=  Gson().fromJson(Gson().toJson(user), TutorLegal::class.java)
                    _tutorLegal.postValue(tutor)
                }
                else {typeUser="admins"}


                println("Token en typeUser: "+response.body()?.token.toString())


            }

        }
       return token
    }

     fun uploadMessage(message: Mensaje){
        viewModelScope.launch {
            userRepo.uploadMessage(message)
        }
    }

    fun getCursosFromProfesor(){
        val idProfesor=_profesor.value?.id
        viewModelScope.launch{
            val response=profesorRepo.getCursosFromProfesor(idProfesor!!)
            if(response.isSuccessful){
                val cursos= response.body()
                _profesor.postValue(_profesor.value.apply { this?.cursos=cursos })
            }
        }
    }

    fun getAlumnosFromCurso(idCurso:Int){
        viewModelScope.launch{
            val response= profesorRepo.getAlumnosFromCurso(idCurso)
            if(response.isSuccessful){
                val alumnos= response.body()
                _profesor.postValue(_profesor.value.apply { this?.cursos?.filter{curso -> curso.id==idCurso}?.get(0)?.alumnos=alumnos!!})
            }
        }

    }
}

//    fun userLogin(loginRequestDto: LoginRequestDto):String{
//        var typeUser: String= ""
//        viewModelScope.launch {
//            val response: Response<LoginResponseDto> = repository.userLogin(loginRequestDto)
//            if(response.isSuccessful){
//                typeUser= response.body()?.userType.toString()
//            }
//        }
//        return typeUser
//    }
//
//    fun userRecovery(token: String, userType:String){
//        viewModelScope.launch{
//            val response: Response<Any> = repository.userRecovery(token, userType)
//            val responseBody = Gson().toJson(response.body())
//
//            if(userType=="Profesor"){
//                val profesor= Gson().fromJson(Gson().toJson(response.body()), Profesor::class.java)
//                _profesor.postValue(profesor)
//            }
//            else if (userType=="Tutor legal"){
//                val tutor= Gson().fromJson(Gson().toJson(response.body()), TutorLegal::class.java)
//                _tutorLegal.postValue(tutor)
//            }
//            else {val user:Usuario = Gson().fromJson(Gson().toJson(response.body()), Usuario::class.java)}
//        }
//    }