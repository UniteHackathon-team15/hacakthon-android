package com.hackathon.team15_android.data.remote.api

import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostAPI {

    @GET("/posts/lists")
    suspend fun getPostList(): List<PostResponse>

    @GET("/posts/")
    suspend fun getDetailPost(
        @Query("post-id") postId: Long,
        @Query("post-details-id") postDetailsOd: Long,
    ): DetailPostResponse
}