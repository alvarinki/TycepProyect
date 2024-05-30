package com.example.pruebachats

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pruebachats.databinding.FragmentShowStudentBinding
import com.google.firebase.storage.FirebaseStorage

class ShowStudent : Fragment() {
    private lateinit var binding: FragmentShowStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowStudentBinding.inflate(inflater, container, false)
        val view: View = binding.getRoot()
        binding.ibImageExchange.setOnClickListener{
            //Toast.makeText(requireContext(), "Dio mio", Toast.LENGTH_SHORT).show()
            openImagePicker()
        }
        // Inflate the layout for this fragment
        return view
    }

    //@RequiresApi(Build.VERSION_CODES.TIRAMISU)
    //private fun checkPermissions() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            // Android 13 (API 33)
//            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_PERMISSION)
//            } else {
//                openImagePicker()
//            }
//        } else {
//            // Android 12 y anteriores
//            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
//            } else {
//                openImagePicker()
//            }
//        }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                type = "image/*"
            }
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val storage = FirebaseStorage.getInstance().getReference("Images")
            binding.ivStudent.setImageURI(selectedImageUri) // Corregir aquí
        }
    }
    companion object {
        private const val REQUEST_CODE_PERMISSION = 1001
        private const val REQUEST_CODE_IMAGE_PICKER = 1002
    }

}