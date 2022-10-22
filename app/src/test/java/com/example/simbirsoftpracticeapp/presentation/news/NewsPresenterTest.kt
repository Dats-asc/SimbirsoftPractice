package com.example.simbirsoftpracticeapp.presentation.news

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvents
import com.example.simbirsoftpracticeapp.domain.usecase.GetCategoriesUseCase
import com.example.simbirsoftpracticeapp.domain.usecase.GetEventsUseCase
import com.example.simbirsoftpracticeapp.domain.usecase.UpdateCategoriesUseCase
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsPresenterTest{

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var view: NewsView

    @RelaxedMockK
    lateinit var viewState: `NewsView$$State`

    @RelaxedMockK
    lateinit var getEventsUseCase: GetEventsUseCase

    private lateinit var presenter: NewsPresenter

    @Before
    fun setup() {
        presenter = NewsPresenter(getEventsUseCase)

        with(presenter) {
            attachView(view)
            setViewState(this@NewsPresenterTest.viewState)
        }

        val testScheduler = Schedulers.trampoline()
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
    }

    @Test
    fun getEventsReturnsEmptyList(){
        val emptyList = CharityEvents(listOf())
        every { getEventsUseCase() } returns Single.just(emptyList)

        presenter.getEvents()

        verifySequence {
            viewState.showProgressbar()
            viewState.setEvents(emptyList)
            viewState.hideProgressbar()
        }
    }

    @Test
    fun getEventThrowsErrorShouldShowError(){
        every { getEventsUseCase() } returns Single.error(Throwable("Test"))

        presenter.getEvents()

        verifySequence {
            viewState.showProgressbar()
            viewState.showError(any())
            viewState.hideProgressbar()
        }
    }

    @Test
    fun getEventReturnsData(){
        val data = CharityEvents(listOf(mockk()))
        every { getEventsUseCase() } returns Single.just(data)

        presenter.getEvents()

        verifySequence {
            viewState.showProgressbar()
            viewState.setEvents(data)
            viewState.hideProgressbar()
        }
    }
}