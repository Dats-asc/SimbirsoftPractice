package com.example.simbirsoftpracticeapp.presentation.news

import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory

interface Filterable {

    fun onFiltersChanged(onFiltersChanged: (List<FilterCategory>) -> Unit)
}