package com.staynight.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class AuthRequestBody(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("request_token")
    val requestToken: String
)