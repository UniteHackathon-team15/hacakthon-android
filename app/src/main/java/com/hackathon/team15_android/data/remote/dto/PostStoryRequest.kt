package com.hackathon.team15_android.data.remote.dto

import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.presentation.ui.main.RequestData

data class PostStoryRequest(

    val title : String,
    val summary : String,
    val image : String,
    val post_details_list : List<RequestData>

)


