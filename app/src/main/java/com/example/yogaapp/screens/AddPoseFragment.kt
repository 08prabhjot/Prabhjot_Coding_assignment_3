package com.example.yogaapp.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yogaapp.R
import com.example.yogaapp.databinding.FragmentAddPoseBinding
import com.example.yogaapp.utils.Constants
import com.example.yogaapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.FileNotFoundException


class AddPoseFragment : Fragment() {

    lateinit var binding: FragmentAddPoseBinding
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPoseBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setObservers()
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.imagePicker.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(intent)
        }
    }

    private fun setObservers() {
        viewModel.isPoseAdded.observe(viewLifecycleOwner){
            if(it){
                findNavController().popBackStack()
            }
        }

        viewModel.snackBarText.observe(viewLifecycleOwner){
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if(result.resultCode == AppCompatActivity.RESULT_OK){
            if(result.data != null){
                val imageUri = result.data!!.data!!
                try {
                    val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.profileImage.setImageBitmap(bitmap)
                    viewModel.currentSelectedEncodedImage = Constants.encodeImage(bitmap)

                }catch (e: FileNotFoundException){
                    e.printStackTrace()
                }
            }
        }

    }

}