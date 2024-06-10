package com.bewe.suitmediamobiledev.ui.navigation

sealed class Screen(val route: String) {
    data object First: Screen("first")

    data object Second: Screen("second/{name}") {
        fun createRoute(name: String) = "second/$name"
    }

    data object Third: Screen("third")
}