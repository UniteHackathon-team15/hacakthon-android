package com.hackathon.team15_android.data.repository

import com.hackathon.team15_android.data.remote.api.PostAPI
import javax.inject.Inject

class GetPostListRepository @Inject constructor(
    private val postAPI: PostAPI,
) {
    
}