package com.geekykel.gads.network

import com.geekykel.gads.model.LearnerModel
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

val getDataService by lazy {
    retrofit.create(GetDataService::class.java)
}

interface GetDataService {

    @GET("/api/hours")
    fun getLearnersHours(): Deferred<List<LearnerModel>>

    @GET("/api/skilliq")
    fun getLearnersSkilliq(): Deferred<List<LearnerModel>>

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    fun submitProject(
        @Field("entry.1877115667") firstName: String,
        @Field("entry.2006916086") lastName: String,
        @Field("entry.1824927963") emailAddress: String,
        @Field("entry.284483984") githubLink: String
    ): Call<Void>
}