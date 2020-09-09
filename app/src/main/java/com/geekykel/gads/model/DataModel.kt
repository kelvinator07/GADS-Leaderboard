package com.geekykel.gads.model

import com.google.gson.annotations.SerializedName

data class DataModel (
    @SerializedName("name")
    val name: String,

    @SerializedName("score")
    val score: Int,

    @SerializedName("hours")
    val hours: Int,

    @SerializedName("country")
    val country: String,

    @SerializedName("badgeUrl")
    val badgeUrl: String
)