package com.mathbord.ai.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelDestination(
    val route: AppRoute,
    val label: String,
    val icon: ImageVector
)

val topLevelDestinations = listOf(
    TopLevelDestination(AppRoute.Home, "Inicio", Icons.Outlined.Home),
    TopLevelDestination(AppRoute.Board, "Pizarra", Icons.Outlined.Create),
    TopLevelDestination(AppRoute.History, "Historial", Icons.Outlined.History),
    TopLevelDestination(AppRoute.Exercises, "Ejercicios", Icons.Outlined.FitnessCenter),
    TopLevelDestination(AppRoute.Settings, "Ajustes", Icons.Outlined.Settings)
)
