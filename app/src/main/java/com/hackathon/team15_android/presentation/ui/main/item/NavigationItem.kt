package com.hackathon.team15_android.presentation.ui.main.item

import com.hackathon.team15_android.R

sealed class BottomNavigationItem(var route: String, var icon: Int, var title: String) {
    object Library: BottomNavigationItem("library", R.drawable.ic_library, "도서관")
    object Story: BottomNavigationItem("story", R.drawable.ic_story, "이야기")
    object Publication: BottomNavigationItem("publication", R.drawable.ic_publication, "편찬하기")
}

sealed class NavItem(var route : String){
    object Library: NavItem("library")
    object Story: NavItem("story")
    object Publication: NavItem("publication")
    object Preview: NavItem("preview")
    object Detail: NavItem("detail")
    object Edit : NavItem("Edit")
}

