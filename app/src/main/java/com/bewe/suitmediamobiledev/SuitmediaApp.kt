package com.bewe.suitmediamobiledev

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bewe.suitmediamobiledev.ui.navigation.Screen
import com.bewe.suitmediamobiledev.ui.screens.first.FirstScreen
import com.bewe.suitmediamobiledev.ui.screens.second.SecondScreen
import com.bewe.suitmediamobiledev.ui.screens.third.ThirdScreen

@Composable
fun SuitmediaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.First.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            /* Declare the routes of each screens */

            /* First Screen */
            composable(Screen.First.route) {
                FirstScreen(
                    navigateToSecondScreen = { name ->
                        navController.navigate(Screen.Second.createRoute(name))
                    }
                )
            }

            /* Second Screen */
            composable(
                Screen.Second.route,
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                ),
            ) {
                val name = it.arguments?.getString("name") ?: ""
                val selectedUserName = it.savedStateHandle.get<String>("selectedUserName")
                SecondScreen(
                    name = name,
                    navigateToThirdScreen = { navController.navigate(Screen.Third.route) },
                    navigateBack = { navController.navigateUp() },
                    selectedUserName = selectedUserName
                )
            }

            /* Third Screen */
            composable(Screen.Third.route) {
                ThirdScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onItemClick = { selectedUserName ->
                        /* Sending data to the previous screen */
                        navController.previousBackStackEntry?.savedStateHandle?.set("selectedUserName", selectedUserName)
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}