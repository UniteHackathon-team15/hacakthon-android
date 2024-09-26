package com.hackathon.team15_android.data.repository

import com.hackathon.team15_android.data.remote.api.AiAPI
import com.hackathon.team15_android.data.remote.api.PostAPI
import com.hackathon.team15_android.data.remote.dto.FirstStoryRequest
import com.hackathon.team15_android.data.remote.dto.response.firstStoryResponse
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import javax.inject.Inject

class AiRepository @Inject constructor(
    private val aiAPI: AiAPI,
) {
    suspend fun getFirstStory(question : String) : String {
        return aiAPI.firstStory(FirstStoryRequest(question)).result
    }

//    suspend fun getDetailPost(postId: Long, postDetailId: Long): DetailPostResponse {
//        return postAPI.getDetailPost(postId, postDetailId)
//    }

}