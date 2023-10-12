package com.hackathon.team15_android.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.repository.PostRepository
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
    var detailPost = mutableStateOf<DetailPostResponse?>(null)
    fun getDetailPost(postId: Long, postDetailId: Long) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            postRepository.getDetailPost(postId = postId, postDetailId = postDetailId)
        }.onSuccess {
            detailPost.value = it
        }.onFailure {
            Log.d(TAG, "detailPost에러 - $it ")
        }

    }

}