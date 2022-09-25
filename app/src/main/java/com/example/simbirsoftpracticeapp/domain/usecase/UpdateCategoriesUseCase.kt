package com.example.simbirsoftpracticeapp.domain.usecase

import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.domain.repository.CharityRepository
import javax.inject.Inject

class UpdateCategoriesUseCase @Inject constructor(
    private val charityRepository: CharityRepository
) {

    operator fun invoke(categories: List<FilterCategory>) = charityRepository.updateCategories(categories)
}