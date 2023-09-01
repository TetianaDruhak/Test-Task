package com.my.sweettvtesttask.di.module

import com.my.sweettvtesttask.data.api.VideoService
import com.my.sweettvtesttask.data.repository.video.VideoRepository
import com.my.sweettvtesttask.data.repository.video.VideoRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class VideoModule {

    @Singleton
    @Provides
    fun provideVideoApi(retrofit: Retrofit): VideoService {
        return retrofit.create(VideoService::class.java)
    }

    @Singleton
    @Provides
    fun provideVideoRepository(api: VideoService): VideoRepository {
        return VideoRepositoryImp(api)
    }
}