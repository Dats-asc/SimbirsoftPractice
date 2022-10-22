package com.example.simbirsoftpracticeapp.presentation.news

import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.domain.usecase.GetCategoriesUseCase
import com.example.simbirsoftpracticeapp.domain.usecase.UpdateCategoriesUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NewsFilterPresenterTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var view: NewsFilterView

    @RelaxedMockK
    lateinit var viewState: `NewsFilterView$$State`

    @RelaxedMockK
    lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @RelaxedMockK
    lateinit var updateCategoriesUseCase: UpdateCategoriesUseCase

    private lateinit var presenter: NewsFilterPresenter

    @Before
    fun setup() {
        presenter = NewsFilterPresenter(getCategoriesUseCase, updateCategoriesUseCase)
        with(presenter) {
            attachView(view)
            setViewState(this@NewsFilterPresenterTest.viewState)
        }

        val testScheduler = Schedulers.trampoline()
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
    }

    @Test
    fun getCategoriesWithEmptyList() {
        val emptyList = FilterCategories(listOf())
        every { getCategoriesUseCase() } returns Single.just(emptyList)

        presenter.getCategories()

        verifySequence {
            viewState.showProgressbar()
            viewState.setCategories(FilterCategories(listOf()))
            viewState.hideProgressbar()
        }
    }

    @Test
    fun getCategoriesWithoutData() {
        every { getCategoriesUseCase() } returns Single.error(Throwable())

        presenter.getCategories()

        verifySequence {
            viewState.showProgressbar()
            viewState.hideProgressbar()
            viewState.showError(any())
        }
    }

    @Test
    fun getCategoriesWithData() {
        val testData = FilterCategories(listOf(FilterCategory(0, "TEST", true)))
        every { getCategoriesUseCase() } returns Single.just(testData)

        presenter.getCategories()

        verifySequence {
            viewState.showProgressbar()
            viewState.setCategories(testData)
            viewState.hideProgressbar()
        }
    }

    @Test
    fun updateDataWithEmptyListShouldDoNothing() {
        val emptyList = listOf<FilterCategory>()
        every { updateCategoriesUseCase(any()) } just runs

        presenter.updateCategories(emptyList)

        verify(exactly = 0) {
            updateCategoriesUseCase(emptyList)
        }
    }

    @Test
    fun updateDataWithDataShouldUpdate() {
        val data = listOf(FilterCategory(0, "test", true))
        every { updateCategoriesUseCase(not(listOf())) } just runs

        presenter.updateCategories(data)

        verify(exactly = 1) {
            updateCategoriesUseCase(data)
        }
    }

}