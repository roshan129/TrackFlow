package com.roshanadke.trackflow.common

import java.lang.StringBuilder

sealed class Screen(val route: String) {

    object OnboardingScreen: Screen("OnboardingScreen")
    object MainScreen: Screen("MainScreen")
    object StatisticsScreen: Screen("StatisticsScreen")
    object TrackScreen: Screen("TrackScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
