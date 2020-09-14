package com.geekykel.gads.repository

import androidx.lifecycle.MutableLiveData
import com.geekykel.gads.model.ResultWrapper
import com.geekykel.gads.model.UserModel
import com.geekykel.gads.network.GetDataService
import com.geekykel.gads.network.getDataService
import retrofit2.Call
import retrofit2.Response

val userRepository by lazy {
    UserRepository(getDataService)
}

class UserRepository(private val webService: GetDataService) {

    fun submit(user: UserModel, resultWrapper: MutableLiveData<ResultWrapper<Any>>) {

        webService
            .submitProject(user.email, user.firstName, user.lastName, user.projectLink)
            .enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful)
                        resultWrapper.postValue(ResultWrapper.Success(null))
                    else
                        resultWrapper.postValue(ResultWrapper.GenericError(response.code(), null))
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    resultWrapper.postValue(ResultWrapper.NetworkError)
                }
            })

    }
}