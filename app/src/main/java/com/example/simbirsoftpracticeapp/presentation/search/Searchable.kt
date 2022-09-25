package com.example.simbirsoftpracticeapp.presentation.search

import com.jakewharton.rxbinding4.InitialValueObservable

interface Searchable {

    var searchViewListener: InitialValueObservable<CharSequence>?
}