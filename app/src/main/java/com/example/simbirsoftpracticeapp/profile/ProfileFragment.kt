package com.example.simbirsoftpracticeapp.profile

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.*
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val startCameraForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                handleCameraIntent(result)
            }
        }

    private val startGalleryPhotoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                handlePickedImageIntent(result)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(inflater, container, false).let {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.profilePhotoHolder.setOnClickListener {
            showAlertDialog()
        }
        Utils.loadImage(binding.ivFriend1, Constants.TEST_PROFILE_FRIEND_IMAGE_1)
        Utils.loadImage(binding.ivFriend2, Constants.TEST_PROFILE_FRIEND_IMAGE_2)
        Utils.loadImage(binding.ivFriend3, Constants.TEST_PROFILE_FRIEND_IMAGE_3)
    }

    private fun showAlertDialog() {
        val dialog = ProfileAlertDialogView(requireContext())
        dialog.onItemSelected { actionId ->
            when (actionId) {
                R.id.action_upload -> {
                    openGallery()
                }
                R.id.action_make_photo -> {
                    dispatchTakePictureIntent()
                }
                R.id.action_delete -> {
                    binding.ivProfilePhoto.visibility = View.INVISIBLE
                }
            }
        }
        dialog.showDialog()
    }

    private fun openGallery() {
        val galleryIntent = Intent().apply {
            action = ACTION_OPEN_DOCUMENT
            type = IMAGE_TYPE
        }
        try {
            startGalleryPhotoResult.launch(galleryIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e("", e.message.orEmpty())
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startCameraForResult.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e("", e.message.orEmpty())
        }
    }

    private fun handlePickedImageIntent(result: ActivityResult) {
        val uri = result.data?.data
        requireActivity().applicationContext.contentResolver.takePersistableUriPermission(
            uri!!,
            FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION
        )

        binding.ivProfilePhoto.setImageURI(uri)
    }

    private fun handleCameraIntent(result: ActivityResult) {
        val image = result.data?.extras?.get("data") as Bitmap
        binding.ivProfilePhoto.setImageBitmap(image)
    }

    companion object {
        private const val IMAGE_TYPE = "image/*"
    }

}