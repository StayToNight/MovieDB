package com.staynight.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class AddToWatchlistRequestBody(
    @SerializedName("media_id")
    val mediaId: Int,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("watchlist")
    val watchlist: Boolean
)