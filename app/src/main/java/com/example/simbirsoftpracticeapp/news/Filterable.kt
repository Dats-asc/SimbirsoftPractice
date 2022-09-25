package com.example.simbirsoftpracticeapp.news

import com.example.simbirsoftpracticeapp.news.data.FilterCategory

interface Filterable {

    fun onFiltersChanged(onFiltersChanged: (List<FilterCategory>) -> Unit)
}