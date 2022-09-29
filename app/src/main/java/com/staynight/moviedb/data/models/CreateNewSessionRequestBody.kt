package com.staynight.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class CreateNewSessionRequestBody(
    @SerializedName("request_token")
    val requestToken: String
)
