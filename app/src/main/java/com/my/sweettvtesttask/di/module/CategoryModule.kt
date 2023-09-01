package com.my.sweettvtesttask.di.module

import com.my.sweettvtesttask.data.api.CategoryService
import com.my.sweettvtesttask.data.repository.category.CategoryRepository
import com.my.sweettvtesttask.data.repository.category.CategoryRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class CategoryModule {

    @Singleton
    @Provides
    fun provideCategoryApi(retrofit: Retrofit) : CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(api: CategoryService) : CategoryRepository {
        return CategoryRepositoryImp(api)
    }
}