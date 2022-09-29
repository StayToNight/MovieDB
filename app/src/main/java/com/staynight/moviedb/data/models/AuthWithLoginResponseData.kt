package com.staynight.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class AuthWithLoginResponseData(
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("request_token")
    val requestToken: String,
    @SerializedName("success")
    val success: Boolean
)