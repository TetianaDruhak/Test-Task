package com.my.sweettvtesttask.domain.usecase

import com.my.sweettvtesttask.data.repository.category.CategoryRepository
import com.my.sweettvtesttask.domain.response.CategoryResponse
import com.my.sweettvtesttask.domain.usecase.base.BaseUseCase
import com.my.sweettvtesttask.utils.Res
import javax.inject.Inject

class GetAllCategoriesUseCase
@Inject constructor(private val categoryRepository: CategoryRepository) :
    BaseUseCase<Unit?, Res<List<CategoryResponse>>>() {

    override suspend fun executeOnBackground(data: Unit?): Res<List<CategoryResponse>> {
        return categoryRepository.getAllCategory()
    }
}