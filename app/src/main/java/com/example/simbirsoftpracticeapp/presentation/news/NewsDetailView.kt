package com.example.simbirsoftpracticeapp.presentation.news

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface NewsDetailView : MvpView {

    @AddToEndSingle
    fun setEventDetail(event: CharityEvent)

    @OneExecution
    fun showProgressbar()

    @OneExecution
    fun hideProgressbar()
}