package com.example.obukesingleactivityapplication.ui.add_activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.obukesingleactivityapplication.BuildConfig
import com.example.obukesingleactivityapplication.databinding.FragmentAddActivityBinding
import com.example.obukesingleactivityapplication.models.ContentUriRequestBody
import com.example.obukesingleactivityapplication.showInfoSnackBar
import com.example.obukesingleactivityapplication.transformIntoDatePicker
import okhttp3.MultipartBody
import java.io.File

class AddActivityFragment: Fragment() {

    private var _binding: FragmentAddActivityBinding? = null
    private val binding get() = _binding!!
    private var cameraPhotoUri: Uri? = null
    private lateinit var viewModel: AddActivityViewModel
    private var bearerToken: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddActivityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AddActivityViewModel::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        bearerToken = sharedPreferences.getString("BEARER_TOKEN", "")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            activityDate.transformIntoDatePicker(requireContext(), "dd.MM.yyyy.", null)
            activityEvidenceImageView.setOnClickListener{
                takeImage()
            }
            addActivityButton.setOnClickListener{
                if(isValidInput()){
                    addActivityButton.alpha = 1f
                    addActivity()
                } else {
                    addActivityButton.alpha = 0.2f
                }
            }
        }

        viewModel.addActivityStatus.observe(viewLifecycleOwner, {
            it?.let {
                if(it) {
                    binding.root.showInfoSnackBar("ACTIVITY ADDED SUCCESSFULLY.")
                    findNavController().popBackStack()
                } else {
                    binding.root.showInfoSnackBar("ERROR ON ADDING ACTIVITY.")
                }
            }
        })

    }

    private fun takeImage() {
        cameraPhotoUri = getTemporaryFileUri()
        takeImageResult.launch(cameraPhotoUri)
    }

    private fun isValidInput(): Boolean {
        with(binding) {
            if(activityDate.text.toString().isEmpty()){
                root.showInfoSnackBar("YOU NEED TO PICK A DATE")
                return false
            }
            if(activityTitle.text.toString().isEmpty()){
                root.showInfoSnackBar("YOU NEED TO ENTER THE TITLE")
                return false
            }
            if(cameraPhotoUri == null){
                root.showInfoSnackBar("YOU NEED TO PICK A PHOTO")
                return false
            }
        }
        return true
    }

    private fun addActivity() {
        with(binding) {
            val title = activityTitle.text.toString()
            val date = activityDate.text.toString()
            cameraPhotoUri?.let {
                val imageBody = ContentUriRequestBody(requireContext().contentResolver, it)
                val imagePart = MultipartBody.Part.createFormData("evidence_images[]","${System.currentTimeMillis()}", imageBody)
                bearerToken?.let { token ->
                    viewModel.addActivity(token, title, date, imagePart)
                }
            }
        }
    }

    private fun getTemporaryFileUri(): Uri {
        val tempFile = File.createTempFile("temp_image_file", ".png", requireActivity().cacheDir)
        tempFile.createNewFile()
        tempFile.deleteOnExit()

        return FileProvider.getUriForFile(
            requireContext().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tempFile
        )
    }

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                cameraPhotoUri?.let {
                    binding.activityEvidenceImageView.setImageURI(it)
                }
            }
        }
}