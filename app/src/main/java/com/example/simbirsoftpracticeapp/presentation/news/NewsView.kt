package com.example.simbirsoftpracticeapp.presentation.news

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvents
import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface NewsView : MvpView {

    @OneExecution
    fun setEvents(events: CharityEvents)

    @OneExecution
    fun showProgressbar()

    @OneExecution
    fun hideProgressbar()

    @OneExecution
    fun showError(msg: String)
}