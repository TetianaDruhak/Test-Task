package com.my.sweettvtesttask.data.repository.base

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.my.sweettvtesttask.utils.ErrorModel
import com.my.sweettvtesttask.utils.Res
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

open class BaseRepository {

    protected fun <T, G> handleResponse(
        response: Response<G>,
        configureResponse: (response: Response<G>) -> T
    ): Res<T> {
        return if (response.isSuccessful) {
            Res.SUCCESS(configureResponse.invoke(response))
        } else {
            if (response.errorBody() != null) {
                val error = response.errorBody()!!.charStream().readText()
                try {
                    val jsonObj = JSONObject(error)
                    Res.ERROR(
                        ErrorModel.ServerError(
                            response.code(), jsonObj.getString(
                                jsonObj.names()
                                    ?.get(0) as String
                            )
                        )
                    )
                } catch (e: JSONException) {
                    if (error.isNotEmpty()) {
                        Res.ERROR(ErrorModel.ServerError(response.code(), error))
                    } else {
                        Res.ERROR(ErrorModel.ServerError(response.code(), response.message()))
                    }
                }
            } else {
                Res.ERROR(ErrorModel.ServerError(response.code(), response.message()))
            }
        }
    }

    protected fun <T> handleResponse(
        response: Response<T>
    ): Res<T> {
        return handleResponse(response) { response.body()!! }
    }

    protected fun <T> deserializeObject(
        response: Response<JsonObject>,
        key: String,
        type: Class<T>
    ): T {
        val notificationSettingsBody = response.body()!!.get(key)
        return Gson().fromJson(notificationSettingsBody, type)
    }

}