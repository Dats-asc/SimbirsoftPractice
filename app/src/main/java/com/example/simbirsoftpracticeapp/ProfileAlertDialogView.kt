package com.example.simbirsoftpracticeapp

import android.app.AlertDialog
import android.content.Context
import android.widget.LinearLayout

class ProfileAlertDialogView(context: Context) : AlertDialog(context) {

    private var alertView = layoutInflater.inflate(R.layout.profile_custom_dialog, null)

    private var alertDialog: AlertDialog? = null

    private var onItemSelected: ((Int) -> Unit)? = null

    private val optionUpdate by lazy {
        alertView.findViewById<LinearLayout>(R.id.action_upload)
    }

    private val optionMakePhoto by lazy {
        alertView.findViewById<LinearLayout>(R.id.action_make_photo)
    }

    private val optionDelete by lazy {
        alertView.findViewById<LinearLayout>(R.id.action_delete)
    }

    init {
        val builder = Builder(context)
        builder.setView(alertView)
        alertDialog = builder.create()
    }

    private fun setListeners() {
        optionUpdate.setOnClickListener { onItemSelected?.invoke(R.id.action_upload) }
        optionMakePhoto.setOnClickListener { onItemSelected?.invoke(R.id.action_make_photo) }
        optionDelete.setOnClickListener { onItemSelected?.invoke(R.id.action_delete) }
    }

    fun showDialog() {
        alertDialog?.show()
    }

    fun onItemSelected(action: (Int) -> Unit) {
        onItemSelected = action
        setListeners()
    }


}