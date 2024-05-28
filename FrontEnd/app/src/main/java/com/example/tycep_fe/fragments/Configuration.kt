package com.example.tycep_fe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tycep_fe.databinding.FragmentConfigurationBinding


class Configuration : Fragment() {
    private var _binding: FragmentConfigurationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)


        val root: View = _binding!!.root
        return root
    }


}