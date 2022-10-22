package com.example.simbirsoftpracticeapp.presentation.news

import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface NewsFilterView : MvpView {

    @AddToEndSingle
    fun setCategories(categories: FilterCategories)

    @OneExecution
    fun showProgressbar()

    @OneExecution
    fun hideProgressbar()

    @OneExecution
    fun showError(msg: String)
}