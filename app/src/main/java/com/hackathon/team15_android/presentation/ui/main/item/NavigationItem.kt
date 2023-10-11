package com.hackathon.team15_android.presentation.ui.main.item

import com.hackathon.team15_android.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Library: NavigationItem("library", R.drawable.ic_library, "도서관")
    object Story: NavigationItem("story", R.drawable.ic_story, "이야기")
    object Publication: NavigationItem("publication", R.drawable.ic_publication, "편찬하기")
}