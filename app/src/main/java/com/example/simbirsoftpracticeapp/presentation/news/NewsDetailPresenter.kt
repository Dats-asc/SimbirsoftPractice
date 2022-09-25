package com.example.simbirsoftpracticeapp.presentation.news

import android.util.Log
import com.example.simbirsoftpracticeapp.domain.usecase.GetEventByIdUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class NewsDetailPresenter @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase
) : MvpPresenter<NewsDetailView>() {

    private val disposables = CompositeDisposable()

    fun getEvent(id: Int) {
        disposables.add(
            getEventByIdUseCase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgressbar() }
                .doOnSuccess { viewState.hideProgressbar() }
                .subscribe({ event ->
                    viewState.setEventDetail(event)
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