package com.example.simbirsoftpracticeapp.domain.usecase

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.repository.CharityRepository
import com.example.simbirsoftpracticeapp.presentation.search.SearchResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchEventsUseCase @Inject constructor(
    private val charityRepository: CharityRepository
) {

    operator fun invoke(request: String): Single<List<CharityEvent>> {
        return charityRepository.searchEvents(request)
    }
}