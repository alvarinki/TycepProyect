import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tycep_fe.databinding.FragmentDataInsertBinding
import com.example.tycep_fe.viewModels.AdminViewModel
import java.io.File

class DataInsertFragment : Fragment() {
//    private var _binding: FragmentDataInsertBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var adminViewModel: AdminViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentDataInsertBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adminViewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
//
//        binding.btnFilePicker.setOnClickListener {
//            showFileChooser()
//        }
//    }
//
//    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val data: Intent? = result.data
//            if (data != null) {
//                val uri: Uri? = data.data
//                if (uri != null) {
//                    val path = getFileFromUri(uri)
//                    val file = File(path!!)
//                    adminViewModel.registerProfesores(file) { response ->
//                        binding.textView.text = response
//                    }
//                }
//            }
//        }
//    }
//
//    private fun getFileFromUri(uri: Uri): String? {
//        val filePathColumn = arrayOf(android.provider.MediaStore.Images.Media.DATA)
//        val cursor = requireActivity().contentResolver.query(uri, filePathColumn, null, null, null)
//        cursor?.moveToFirst()
//        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
//        val filePath = columnIndex?.let { cursor.getString(it) }
//        cursor?.close()
//        return filePath
//    }
//
//    private fun showFileChooser() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*/*"
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//
//        try {
//            filePickerLauncher.launch(Intent.createChooser(intent, "Selecciona un archivo"))
//        } catch (exception: Exception) {
//            Toast.makeText(requireContext(), "Por favor instala un gestor de archivos", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
