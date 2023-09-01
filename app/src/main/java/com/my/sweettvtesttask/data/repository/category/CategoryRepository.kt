package com.my.sweettvtesttask.data.repository.category

import com.my.sweettvtesttask.domain.response.CategoryResponse
import com.my.sweettvtesttask.utils.Res

interface CategoryRepository {

    suspend fun getAllCategory(): Res<List<CategoryResponse>>

}