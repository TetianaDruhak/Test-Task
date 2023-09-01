package com.my.sweettvtesttask.utils

sealed class Res<T> {
    class LOADING<T> : Res<T>()
    data class SUCCESS<T>(val data: T) : Res<T>()
    data class ERROR<T>(val error: ErrorModel) : Res<T>()
}