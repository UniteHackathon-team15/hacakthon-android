package com.hackathon.team15_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PostListViewModel: ViewModel() {
    var postList = mutableListOf<PostResponse>()
        private set


}