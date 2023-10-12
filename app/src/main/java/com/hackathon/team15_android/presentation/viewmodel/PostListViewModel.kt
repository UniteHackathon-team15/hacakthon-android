package com.hackathon.team15_android.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import com.hackathon.team15_android.data.repository.GetPostListRepository
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostListRepository: GetPostListRepository
) : ViewModel() {
    var postList by mutableStateOf<List<PostResponse>?>(null)

    fun getPostList() = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            getPostListRepository.getPostList()
        }.onSuccess {
            postList = it
        }.onFailure {
            postList = null
            Log.d(TAG, "getPostList에러 - $it ")
        }
    }
}