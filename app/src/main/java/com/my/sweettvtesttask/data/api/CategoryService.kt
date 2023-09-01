package com.my.sweettvtesttask.data.api

import com.my.sweettvtesttask.domain.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET("categories/all")
    suspend fun getAllCategories(): Response<List<CategoryResponse>>
}