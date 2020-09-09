package com.geekykel.gads.network

import com.geekykel.gads.model.DataModel
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("/api/hours")
    fun getHours(): Call<List<DataModel>>

    @GET("/api/skilliq")
    fun getSkilliq(): Call<List<DataModel>>
}