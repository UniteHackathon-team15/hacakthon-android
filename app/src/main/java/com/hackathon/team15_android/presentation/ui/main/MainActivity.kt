package com.hackathon.team15_android.presentation.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hackathon.team15_android.presentation.base.BaseActivity
import com.hackathon.team15_android.presentation.ui.theme.Team15_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Team15_androidTheme {
                mainScreenView()
            }
        }
    }

    @Composable
    fun mainScreenView() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { bottomNavigation(navController = navController) }
        ) {
            Box(Modifier.padding(it)) {
                navigationGraph(navController = navController)
            }
        }
    }

    @Composable
    fun bottomNavigation(navController: NavHostController) {
        val items = listOf<>(

        )
    }

    @Composable
    fun navigationGraph(navController: NavHostController) {

    }

    override fun init() {
        TODO("Not yet implemented")
    }
}