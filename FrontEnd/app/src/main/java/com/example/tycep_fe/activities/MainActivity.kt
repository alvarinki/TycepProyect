package com.example.tycep_fe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.ActivityMainBinding
import com.example.tycep_fe.viewModels.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel:UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this)[UserViewModel::class.java]
        viewModel._profesor.observe(this){profe -> }

    }
}