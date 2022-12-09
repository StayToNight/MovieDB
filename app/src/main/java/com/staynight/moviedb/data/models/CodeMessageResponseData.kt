package com.staynight.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class CodeMessageResponseData(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String
)