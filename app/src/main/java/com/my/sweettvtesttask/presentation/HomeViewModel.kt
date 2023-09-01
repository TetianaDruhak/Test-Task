package com.my.sweettvtesttask.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.sweettvtesttask.R
import com.my.sweettvtesttask.domain.response.CategoryResponse
import com.my.sweettvtesttask.domain.response.VideoResponse
import com.my.sweettvtesttask.domain.usecase.GetAllCategoriesUseCase
import com.my.sweettvtesttask.domain.usecase.GetVideosByCategoryUseCase
import com.my.sweettvtesttask.utils.ErrorModel
import com.my.sweettvtesttask.utils.Res
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getVideosByCategoryUseCase: GetVideosByCategoryUseCase
) : ViewModel() {

    private val categories = MutableLiveData<Res<List<CategoryResponse>>>()
    private val videos = MutableLiveData<Res<List<VideoResponse>>>()

    fun getCategories(): LiveData<Res<List<CategoryResponse>>> = categories

    fun getVideos(): LiveData<Res<List<VideoResponse>>> = videos

    fun loadCategories(context: Context) {
        categories.value = Res.LOADING()
        getAllCategoriesUseCase.execute(null) {
            onComplete {
                categories.value = it
            }

            onNoInternet {
                categories.value =
                    Res.ERROR(ErrorModel.LocalError(context.getString(R.string.no_internet_connection), ErrorModel.LocalErrorStatus.NO_INTERNET))
            }

            onError {
                categories.value =
                    Res.ERROR(ErrorModel.LocalError(context.getString(R.string.something_went_wrong), ErrorModel.LocalErrorStatus.SOMETHING_WRONG))
            }
        }
    }

    fun loadVideos(context: Context, categoryAlias: String) {
        videos.value = Res.LOADING()
        getVideosByCategoryUseCase.execute(categoryAlias) {
            onComplete {
                videos.value = it
            }

            onNoInternet {
                videos.value =
                    Res.ERROR(ErrorModel.LocalError(context.getString(R.string.no_internet_connection), ErrorModel.LocalErrorStatus.NO_INTERNET))
            }

            onError {
                videos.value =
                    Res.ERROR(ErrorModel.LocalError(context.getString(R.string.something_went_wrong), ErrorModel.LocalErrorStatus.SOMETHING_WRONG))
            }
        }
    }

}