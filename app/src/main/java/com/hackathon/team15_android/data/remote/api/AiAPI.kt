package com.hackathon.team15_android.data.remote.api

import com.hackathon.team15_android.data.remote.dto.FirstStoryRequest
import com.hackathon.team15_android.data.remote.dto.response.firstStoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AiAPI {
    @POST("/ai/firstStory")
    suspend fun firstStory(
        @Body question : FirstStoryRequest
    ) : firstStoryResponse

}