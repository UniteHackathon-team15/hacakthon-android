package com.hackathon.team15_android.presentation.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hackathon.team15_android.R
import com.hackathon.team15_android.presentation.base.BaseActivity
import com.hackathon.team15_android.presentation.ui.main.item.NavigationItem
import com.hackathon.team15_android.presentation.ui.main.screen.LibraryScreen
import com.hackathon.team15_android.presentation.ui.main.screen.PublicationScreen
import com.hackathon.team15_android.presentation.ui.main.screen.StoryScreen
import com.hackathon.team15_android.presentation.ui.theme.Team15_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Team15_androidTheme {
                MainScreen()
            }
        }
        init()
    }

    override fun init() {

    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val items = listOf(
            NavigationItem.Library,
            NavigationItem.Story,
            NavigationItem.Publication,
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white),
            contentColor = Color.Black
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray.copy(0.4f),
                    selected = false,
                )
            }
        }
    }


    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = NavigationItem.Library.route) {
            composable(NavigationItem.Library.route) {
                LibraryScreen()
            }
            composable(NavigationItem.Story.route) {
                StoryScreen()
            }
            composable(NavigationItem.Publication.route) {
                PublicationScreen()
            }
        }
    }
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController = navController)
                }
            },
            backgroundColor = colorResource(id = R.color.white)
        )
    }
}
