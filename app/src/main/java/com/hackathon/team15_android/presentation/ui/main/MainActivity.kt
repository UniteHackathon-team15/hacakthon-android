package com.hackathon.team15_android.presentation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hackathon.team15_android.R
import com.hackathon.team15_android.presentation.ui.main.data.Test
import com.hackathon.team15_android.presentation.ui.main.item.BottomNavigationItem
import com.hackathon.team15_android.presentation.ui.main.item.NavItem
import com.hackathon.team15_android.presentation.ui.main.screen.DetailLibraryScreen
import com.hackathon.team15_android.presentation.ui.main.screen.LibraryScreen
import com.hackathon.team15_android.presentation.ui.main.screen.PublicationScreen
import com.hackathon.team15_android.presentation.ui.main.screen.StoryScreen
import com.hackathon.team15_android.presentation.viewmodel.PostListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val items = listOf(
            BottomNavigationItem.Library,
            BottomNavigationItem.Story,
            BottomNavigationItem.Publication,
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },

                    )
            }
        }
    }


    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = NavItem.Library.route) {
            composable(NavItem.Library.route) {
                LibraryScreen(navController, PostListViewModel())
            }
            composable(NavItem.Story.route) {
                StoryScreen()
            }
            composable(NavItem.Publication.route) {
                PublicationScreen()
            }
            composable(NavItem.Detail.route) {
                DetailLibraryScreen(navController = rememberNavController())
            }
        }
    }

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
