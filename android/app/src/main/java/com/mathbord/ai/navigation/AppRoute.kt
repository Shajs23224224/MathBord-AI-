package com.mathbord.ai.navigation

sealed interface AppRoute {
    val route: String
    data object Home : AppRoute { override val route = "home" }
    data object Board : AppRoute { override val route = "board" }
    data object History : AppRoute { override val route = "history" }
    data object Exercises : AppRoute { override val route = "exercises" }
    data object Settings : AppRoute { override val route = "settings" }
}
