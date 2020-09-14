package com.geekykel.gads.model

import com.google.gson.annotations.SerializedName

data class LearnerModel(
    val name: String,
    @SerializedName(value = "number", alternate = ["hours", "score"])
    val number: Int,
    val country: String,
    val badgeUrl: String
)