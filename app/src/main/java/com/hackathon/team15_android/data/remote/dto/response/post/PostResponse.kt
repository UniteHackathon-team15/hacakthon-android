package com.hackathon.team15_android.data.remote.dto.response.post

import com.google.gson.annotations.SerializedName



data class PostBodyResponse(
    val post_list : List<PostResponse>
)
data class PostResponse (
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("image")
    val image: String,
)