package com.company.rickmortyverse.presentation.main

import CreateCharacterScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.company.rickmortyverse.presentation.localCharacterScreen.UserCharacterScreen
import com.company.rickmortyverse.presentation.characters.HomeScreen
import com.company.rickmortyverse.presentation.explore.ExploreScreen
import com.company.rickmortyverse.ui.theme.RickMortyVerseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyVerseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(innerPadding)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = Screen.ApiCharacterScreen.route) {
            composable(Screen.ApiCharacterScreen.route) { HomeScreen() }
            composable(Screen.UserCharacterScreen.route) { UserCharacterScreen() }
            composable(Screen.CreateCharacterScreen.route) { CreateCharacterScreen() }
            composable(Screen.CustomScreen.route) { ExploreScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.ApiCharacterScreen,
        Screen.UserCharacterScreen,
        Screen.CreateCharacterScreen,
        Screen.CustomScreen
    )

    BottomNavigation {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}