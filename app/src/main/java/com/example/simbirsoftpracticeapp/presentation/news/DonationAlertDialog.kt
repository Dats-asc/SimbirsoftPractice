package com.example.simbirsoftpracticeapp.presentation.news

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.simbirsoftpracticeapp.R

class DonationAlertDialog(
    context: Context,
    listener: DonationAlertDialog.Listener
) : AlertDialog(context) {

    private var alertView = layoutInflater.inflate(R.layout.donation_dialog, null).also { alert ->

        val amountOfMoneyEditText = alert?.findViewById<EditText>(R.id.et_amount_of_money)
        val btnSend = alert.findViewById<Button>(R.id.btn_send)

        alert?.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener {
            alertDialog?.dismiss()
        }

        btnSend?.setOnClickListener {
            listener.onSend(amountOfMoneyEditText?.text.toString().toDoubleOrNull() ?: 0.0)
            alertDialog?.dismiss()
        }

        amountOfMoneyEditText?.addTextChangedListener { text ->
            val amount = text.toString().toDoubleOrNull()
            amount?.let {
                btnSend.isEnabled = it in 1.0..9_999_999.0
            } ?: kotlin.run {
                btnSend.isEnabled = false
            }
        }
    }

    private var alertDialog: android.app.AlertDialog? = null

    init {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setView(alertView)
        alertDialog = builder.create()
    }

    override fun dismiss() {
        super.dismiss()
        alertDialog?.dismiss()
    }

    fun showDialog() {
        alertDialog?.show()
    }

    interface Listener {

        fun onSend(amountOfMoney: Double)
    }
}