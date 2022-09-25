package com.example.simbirsoftpracticeapp.presentation.news

import android.util.Log
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.domain.usecase.GetCategoriesUseCase
import com.example.simbirsoftpracticeapp.domain.usecase.UpdateCategoriesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NewsFilterPresenter @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase
) : MvpPresenter<NewsFilterView>() {

    private val disposables = CompositeDisposable()

    fun getCategories() {
        disposables.add(
            getCategoriesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgressbar() }
                .doAfterSuccess { viewState.hideProgressbar() }
                .subscribe({ categories ->
                    viewState.setCategories(categories)
                }, { e ->
                    Log.e("NewsFilterPresenter", e.message.orEmpty())
                })
        )
    }

    fun updateCategories(categories: List<FilterCategory>) {
        updateCategoriesUseCase(categories)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        disposables.dispose()
    }
}