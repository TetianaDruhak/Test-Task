package com.my.sweettvtesttask.domain.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: VideoType,
    @SerializedName("title") val title: String,
    @SerializedName("poster_url") val posterUrl: String,
    @SerializedName("video_url") val videoUrl: String,
    @SerializedName("drm_scheme") val drmScheme: String,
    @SerializedName("drm_license_url") val drmLicenseUrl: String
) : Parcelable

@Parcelize
enum class VideoType : Parcelable { DASH, HLS, MP4 }