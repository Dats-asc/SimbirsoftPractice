package com.example.simbirsoftpracticeapp.presentation.search

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface EventsTabView : MvpView {

    @AddToEndSingle
    fun updateList(events: List<CharityEvent>)

    @OneExecution
    fun showPlaceholder()

    @OneExecution
    fun hidePlaceholder()
}