package com.company.rickmortyverse.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

// Define a sealed class for all screens
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    // Each screen is defined as an object within the sealed class

    object ApiCharacterScreen : Screen(
        route = "api_character_screen",
        title = "Home",
        icon = Icons.Filled.Home
    )

    object UserCharacterScreen : Screen(
        route = "user_character_screen",
        title = "My Chars",
        icon = Icons.Filled.Person
    )

    object CreateCharacterScreen : Screen(
        route = "create_character_screen",
        title = "Create",
        icon = Icons.Filled.Add
    )

    object CustomScreen : Screen(
        route = "custom_screen",
        title = "Explore",
        icon = Icons.Filled.Search // Substitute with a similar icon
    )
}