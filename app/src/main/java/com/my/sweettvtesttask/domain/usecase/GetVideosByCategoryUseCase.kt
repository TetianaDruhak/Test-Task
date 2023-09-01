package com.my.sweettvtesttask.domain.usecase

import com.my.sweettvtesttask.data.repository.video.VideoRepository
import com.my.sweettvtesttask.domain.response.VideoResponse
import com.my.sweettvtesttask.domain.usecase.base.BaseUseCase
import com.my.sweettvtesttask.utils.Res
import javax.inject.Inject

class GetVideosByCategoryUseCase
@Inject constructor(private val videoRepository: VideoRepository) :
    BaseUseCase<String, Res<List<VideoResponse>>>() {

    override suspend fun executeOnBackground(data: String): Res<List<VideoResponse>> {
        return videoRepository.getVideosByCategories(data)
    }

}