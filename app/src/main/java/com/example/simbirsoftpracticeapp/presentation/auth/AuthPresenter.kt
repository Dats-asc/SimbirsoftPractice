package com.example.simbirsoftpracticeapp.presentation.auth

import moxy.MvpPresenter

class AuthPresenter : MvpPresenter<AuthView>() {

    fun signIn(login: String, password: String){
        viewState.onSignInSuccess()
    }
}