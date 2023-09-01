package com.my.sweettvtesttask.data.repository.video

import com.my.sweettvtesttask.data.api.VideoService
import com.my.sweettvtesttask.data.repository.base.BaseRepository
import com.my.sweettvtesttask.domain.response.VideoResponse
import com.my.sweettvtesttask.utils.Res
import javax.inject.Inject

class VideoRepositoryImp @Inject constructor(private val api: VideoService) : VideoRepository,
    BaseRepository() {

    override suspend fun getVideosByCategories(categoryAlias: String): Res<List<VideoResponse>> {
        val response = api.getVideosByCategory(categoryAlias)
        return handleResponse(response) {
            return@handleResponse response.body() ?: listOf()
        }
    }
}