package com.hackathon.team15_android.data.remote.api

import com.hackathon.team15_android.data.remote.dto.PostStoryRequest
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.remote.dto.response.post.PostBodyResponse
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostAPI {

    @GET("/posts/lists")
    suspend fun getPostList(): PostBodyResponse

    @GET("/posts")
    suspend fun getDetailPost(
        @Query("post-id") postId: Long,
        @Query("post-details-id") postDetailsId: Long,
    ): DetailPostResponse

    @POST("/posts")
    suspend fun postStory(
        @Body post : PostStoryRequest
    ) : Void?
}