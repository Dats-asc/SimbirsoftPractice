package com.example.simbirsoftpracticeapp.domain.usecase

import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.repository.CharityRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val charityRepository: CharityRepository
) {

    operator fun invoke(id: Int): Single<CharityEvent> {
        return charityRepository.getEventById(id)
    }
}