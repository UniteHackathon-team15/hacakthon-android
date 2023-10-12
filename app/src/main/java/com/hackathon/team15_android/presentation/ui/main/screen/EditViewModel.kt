package com.hackathon.team15_android.presentation.ui.main.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EditViewModel : ViewModel(){

    var isChanged by mutableStateOf(false)

}