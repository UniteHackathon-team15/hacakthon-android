package com.hackathon.team15_android.data.repository

import com.hackathon.team15_android.data.remote.api.PostAPI
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class GetPostListRepository @Inject constructor(
    private val postAPI: PostAPI,
) {
    suspend fun getPostList(): List<PostResponse> {
        return postAPI.getPostList()
    }



}