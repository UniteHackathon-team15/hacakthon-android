package com.hackathon.team15_android.data.remote.dto.response.post

import com.google.gson.annotations.SerializedName

data class PostResponse (
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("image")
    val image: String,
)