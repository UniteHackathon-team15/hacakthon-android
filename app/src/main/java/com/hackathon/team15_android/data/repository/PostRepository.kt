package com.hackathon.team15_android.data.repository

import com.hackathon.team15_android.data.remote.api.PostAPI
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postAPI: PostAPI,
) {
    suspend fun getPostList(): List<PostResponse> {
        return postAPI.getPostList()
    }

    suspend fun getDetailPost(postId: Long, postDetailId: Long): DetailPostResponse {
        return postAPI.getDetailPost(postId, postDetailId)
    }

}