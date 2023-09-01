package com.my.sweettvtesttask.domain.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResponse(
    @SerializedName("alias") val alias: String,
    @SerializedName("name") val name: String
): Parcelable