package com.example.simbirsoftpracticeapp.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.databinding.FragmentAuthBinding
import com.example.simbirsoftpracticeapp.presentation.main.Navigator
import com.jakewharton.rxbinding4.widget.textChanges
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class AuthFragment : MvpAppCompatFragment(), AuthView {

    private lateinit var binding: FragmentAuthBinding

    private val presenter by moxyPresenter { AuthPresenter() }

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
                presenter.signIn(binding.etEmail.text.toString(), binding.etPassword.toString())
            }
        }
    }

    private fun onTextChanged() {
//        binding.btnSignIn.isEnabled = binding.etEmail.text.length >= 6 && binding.etPassword.text.length >= 6
    }

    override fun onSignInSuccess() {
        (requireActivity() as Navigator).onAuthSuccesses()
    }

    override fun onSignInFailure() {
        TODO("Not yet implemented")
    }
}