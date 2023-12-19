package com.roshanadke.trackflow.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roshanadke.trackflow.presentation.screens.MainScreen
import com.roshanadke.trackflow.presentation.screens.OnboardingScreen
import com.roshanadke.trackflow.presentation.screens.StatisticsScreen
import com.roshanadke.trackflow.presentation.screens.TrackScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.OnboardingScreen.route) {
        composable(Screen.OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.StatisticsScreen.route) {
            StatisticsScreen(navController)
        }
        composable(Screen.TrackScreen.route) {
            TrackScreen(navController)
        }
    }

}