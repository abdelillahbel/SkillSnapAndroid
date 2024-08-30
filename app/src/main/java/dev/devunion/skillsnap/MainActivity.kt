/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.devunion.skillsnap.navigation.RootNav
import dev.devunion.skillsnap.navigation.ScreenRoutes

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            RootNav(
                navController = navController
            )
//            val currentApiVersion = Build.VERSION.SDK_INT
//            if (currentApiVersion < Build.VERSION_CODES.S) {
//                navigateToSplashScreen()
//            }
        }
    }

//    private fun navigateToSplashScreen() {
//        navController.popBackStack()
//        navController.navigate(route = ScreenRoutes.SplashScreen.route)
//    }
//
//
//    private fun navigateToMainScreen() {
//        navController.popBackStack()
//        navController.navigate(route = ScreenRoutes.MainScreen.route)
//    }
//
//    private fun navigateToLoginScreen() {
//        navController.popBackStack()
//        navController.navigate(route = ScreenRoutes.LoginScreen.route)
//    }
}
