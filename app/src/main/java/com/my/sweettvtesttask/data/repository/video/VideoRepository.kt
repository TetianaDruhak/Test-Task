package com.my.sweettvtesttask.data.repository.video

import com.my.sweettvtesttask.domain.response.VideoResponse
import com.my.sweettvtesttask.utils.Res

interface VideoRepository {

    suspend fun getVideosByCategories(categoryAlias: String): Res<List<VideoResponse>>
}