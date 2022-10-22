package com.example.simbirsoftpracticeapp.presentation.news

import android.util.Log
import com.example.simbirsoftpracticeapp.domain.usecase.GetEventsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : MvpPresenter<NewsView>() {

    private val disposables = CompositeDisposable()

    fun getEvents() {
        disposables.add(
            getEventsUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgressbar() }
                .doAfterSuccess { viewState.hideProgressbar() }
                .subscribe({ events ->
                    viewState.setEvents(events)
                }, { e ->
                    viewState.showError(e.message.orEmpty())
                    viewState.hideProgressbar()
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        disposables.dispose()
    }
}