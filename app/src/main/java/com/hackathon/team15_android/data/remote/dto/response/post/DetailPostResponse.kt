package com.hackathon.team15_android.data.remote.dto.response.post

import com.google.gson.annotations.SerializedName

data class DetailPostResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("first_option_item")
    val firstOptionItem: Int,
    @SerializedName("first_option_content")
    val firstOptionContent: String,
    @SerializedName("second_option_item")
    val secondOptionItem: Int,
    @SerializedName("second_option_content")
    val secondOptionContent: String,
    @SerializedName("third_option_item")
    val thirdOptionItem: Int,
    @SerializedName("third_option_content")
    val thirdOptionContent: String,
)