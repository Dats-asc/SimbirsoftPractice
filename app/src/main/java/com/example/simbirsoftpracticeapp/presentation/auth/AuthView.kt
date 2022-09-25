package com.example.simbirsoftpracticeapp.presentation.auth

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface AuthView : MvpView {

    @OneExecution
    fun onSignInSuccess()

    @OneExecution
    fun onSignInFailure()
}