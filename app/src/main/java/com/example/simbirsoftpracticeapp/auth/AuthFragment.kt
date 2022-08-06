package com.example.simbirsoftpracticeapp.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Navigator
import com.example.simbirsoftpracticeapp.databinding.FragmentAuthBinding
import com.jakewharton.rxbinding4.widget.textChangeEvents
import com.jakewharton.rxbinding4.widget.textChanges

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAuthBinding.inflate(inflater, container, false).let {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_STRING, binding.etEmail.text.toString())
        outState.putString(PASSWORD_STRING, binding.etPassword.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.etEmail.setText(savedInstanceState?.getString(EMAIL_STRING))
        binding.etPassword.setText(savedInstanceState?.getString(PASSWORD_STRING))
    }

    private fun init() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }

            binding.etEmail.textChanges()
                .subscribe {
                    onTextChanged()
                }
            binding.etPassword.textChanges()
                .subscribe {
                    onTextChanged()
                }

            btnSignIn.setOnClickListener {
                (requireActivity() as Navigator).onAuthSuccesses()
            }
        }
    }

    private fun onTextChanged() {
//        binding.btnSignIn.isEnabled = binding.etEmail.text.length >= 6 && binding.etPassword.text.length >= 6
    }

    companion object {
        private const val EMAIL_STRING = "EMAIL_STRING"
        private const val PASSWORD_STRING = "PASSWORD_STRING"
    }
}