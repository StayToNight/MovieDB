package com.staynight.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class CreateNewSessionResponseData(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("success")
    val success: Boolean
)
