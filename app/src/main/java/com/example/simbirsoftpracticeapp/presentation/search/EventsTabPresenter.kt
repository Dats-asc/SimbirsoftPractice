package com.example.simbirsoftpracticeapp.presentation.search

import android.util.Log
import com.example.simbirsoftpracticeapp.domain.usecase.GetEventsUseCase
import com.example.simbirsoftpracticeapp.domain.usecase.SearchEventsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class EventsTabPresenter @Inject constructor(
    private val searchEventsUseCase: SearchEventsUseCase
) : MvpPresenter<EventsTabView>() {

    private val disposables = CompositeDisposable()

    fun search(request: String) {
        disposables.add(
            searchEventsUseCase(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doOnSuccess { events ->
                    if (events.isEmpty()) {
                        viewState.showPlaceholder()
                    } else {
                        viewState.hidePlaceholder()
                    }
                }
                .subscribe({ searchResults ->
                    viewState.updateList(searchResults)
                }, { e ->
                    Log.e("", e.message.orEmpty())
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        disposables.dispose()
    }
}