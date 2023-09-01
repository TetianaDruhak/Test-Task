package com.my.sweettvtesttask.data.api

import com.my.sweettvtesttask.domain.response.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VideoService {

    @GET("categories/{alias}/videos")
    suspend fun getVideosByCategory(
        @Path("alias") categoryAlias: String
    ): Response<List<VideoResponse>>
}