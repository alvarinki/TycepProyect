package com.example.tycep_fe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
        //loadFragment(com.example.tycep_fe.fragments.UploadFragment())
        viewModel= ViewModelProvider(this)[UserViewModel::class.java]
        viewModel._profesor.observe(this){}

    }

    private fun loadFragment(fragment: Fragment) {
        // Create a fragment transaction
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        // Replace the content of the container with the new fragment
        transaction.replace(R.id.navHostFragment, fragment)
        // Commit the transaction
        transaction.commit()
    }
}