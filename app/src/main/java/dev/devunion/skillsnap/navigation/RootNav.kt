/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.skillsnap.ui.main.MainScreen
import dev.devunion.skillsnap.ui.splash.SplashScreen
import dev.devunion.skillsnap.utils.shouldShowSplashScreen


//            val currentApiVersion = Build.VERSION.SDK_INT
//            if (currentApiVersion < Build.VERSION_CODES.S) {
//                navigateToSplashScreen()
//            }


//    if (currentApiVersion < Build.VERSION_CODES.S) {
//        ScreenRoutes.SplashScreen.route
//    }

//when (user) {
//    null -> ScreenRoutes.AuthNav.route
//    else -> ScreenRoutes.MainNav.route
//}

@Composable
fun RootNav(navController: NavHostController) {
    val user = Firebase.auth.currentUser
    val context = LocalContext.current
    val currentApiVersion = Build.VERSION.SDK_INT
    NavHost(
        navController = navController,
        startDestination = when (shouldShowSplashScreen(currentApiVersion)) {
            true -> ScreenRoutes.SplashScreen.route
            false -> when (user) {
                null -> ScreenRoutes.AuthNav.route
                else -> ScreenRoutes.MainNav.route
            }
        }

    ) {

        authNav(navController)

        composable(route = ScreenRoutes.MainNav.route) {
            MainScreen(navController,
                logout = {
                    navController.navigate(ScreenRoutes.AuthNav.route) {
                        popUpTo(0) {}
                    }
                }
            )
        }
        composable(route = ScreenRoutes.SplashScreen.route) {
            SplashScreen(navController,
                navigateToMainNav = {
                    navController.navigate(ScreenRoutes.MainNav.route) {
                        popUpTo(ScreenRoutes.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToAuthNav = {
                    navController.navigate(ScreenRoutes.AuthNav.route) {
                        popUpTo(ScreenRoutes.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
