package com.example.simbirsoftpracticeapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.simbirsoftpracticeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
            val dialogView = layoutInflater.inflate(R.layout.profile_custom_dialog, null)
            val dialog = AlertDialog.Builder(requireContext()).apply {
                setView(dialogView)
            }.create()
            dialogView.findViewById<LinearLayout>(R.id.action_delete).setOnClickListener {
                binding.ivProfilePhoto.visibility = View.INVISIBLE
                dialog.dismiss()
            }
            dialog.show()
        }
    }

}