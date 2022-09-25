package com.example.simbirsoftpracticeapp.domain.usecase

import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import com.example.simbirsoftpracticeapp.domain.repository.CharityRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val charityRepository: CharityRepository
) {

    operator fun invoke(): Single<FilterCategories> {
        return charityRepository.getCategories()
    }
}