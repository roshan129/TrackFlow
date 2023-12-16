package com.roshanadke.trackflow.common

import java.lang.StringBuilder

sealed class Screen(val route: String) {

    object MainScreen: Screen("MainScreen")
    object StatisticsScreen: Screen("StatisticsScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
