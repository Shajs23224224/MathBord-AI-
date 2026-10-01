package com.mathbord.ai.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mathbord.ai.core.ui.WindowWidthClass
import com.mathbord.ai.core.ui.rememberWindowWidthClass
import com.mathbord.ai.ui.screens.BoardScreen
import com.mathbord.ai.ui.screens.ExercisesScreen
import com.mathbord.ai.ui.screens.HistoryScreen
import com.mathbord.ai.ui.screens.HomeScreen
import com.mathbord.ai.ui.screens.SettingsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val windowWidthClass = rememberWindowWidthClass()

    val goBack: () -> Unit = {
        if (currentRoute != AppRoute.Home.route && navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

    BackHandler(
        enabled = currentRoute != AppRoute.Home.route &&
            navController.previousBackStackEntry != null,
        onBack = goBack
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .onPreviewKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown &&
                    (event.key == Key.Escape ||
                        (event.key == Key.DirectionLeft && event.isAltPressed))
                ) {
                    goBack()
                    true
                } else {
                    false
                }
            },
        bottomBar = {
            if (windowWidthClass == WindowWidthClass.COMPACT) {
                NavigationBar {
                    topLevelDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route.route,
                            onClick = {
                                navController.navigate(destination.route.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(destination.icon, contentDescription = null) },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Row(modifier = Modifier.fillMaxSize()) {
            if (windowWidthClass != WindowWidthClass.COMPACT) {
                NavigationRail {
                    topLevelDestinations.forEach { destination ->
                        NavigationRailItem(
                            selected = currentRoute == destination.route.route,
                            onClick = {
                                navController.navigate(destination.route.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(destination.icon, contentDescription = null) },
                            label = { Text(destination.label) },
                            alwaysShowLabel = windowWidthClass == WindowWidthClass.EXPANDED
                        )
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = AppRoute.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable(AppRoute.Home.route) { HomeScreen() }
                composable(AppRoute.Board.route) { BoardScreen() }
                composable(AppRoute.History.route) { HistoryScreen() }
                composable(AppRoute.Exercises.route) { ExercisesScreen() }
                composable(AppRoute.Settings.route) { SettingsScreen() }
            }
        }
    }
}
