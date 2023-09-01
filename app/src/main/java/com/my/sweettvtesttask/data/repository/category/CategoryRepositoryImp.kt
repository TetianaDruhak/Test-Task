package com.my.sweettvtesttask.data.repository.category

import com.my.sweettvtesttask.data.api.CategoryService
import com.my.sweettvtesttask.data.repository.base.BaseRepository
import com.my.sweettvtesttask.domain.response.CategoryResponse
import com.my.sweettvtesttask.utils.Res
import javax.inject.Inject

class CategoryRepositoryImp @Inject constructor(private val api: CategoryService) : CategoryRepository,
    BaseRepository() {

    override suspend fun getAllCategory(): Res<List<CategoryResponse>> {
        val response = api.getAllCategories()
        return handleResponse(response) {
            return@handleResponse response.body() ?: listOf()
        }
    }

}