package com.hackathon.team15_android.data.remote.dto.response.post

import com.google.gson.annotations.SerializedName

data class DetailPostResponse (
    @SerializedName("content")
    val content: String,
)