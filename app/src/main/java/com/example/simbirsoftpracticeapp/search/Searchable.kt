package com.example.simbirsoftpracticeapp.search

import com.jakewharton.rxbinding4.InitialValueObservable

interface Searchable {

    var searchViewListener: InitialValueObservable<CharSequence>?
}