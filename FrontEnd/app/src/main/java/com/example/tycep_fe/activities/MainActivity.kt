package com.example.tycep_fe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tycep_fe.R
import com.example.tycep_fe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}