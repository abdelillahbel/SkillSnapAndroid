/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.devunion.skillsnap.ui.auth.login.LoginScreen
import dev.devunion.skillsnap.ui.auth.reset_password.ResetPasswordScreen
import dev.devunion.skillsnap.ui.auth.signup.SignUpScreen
import dev.devunion.skillsnap.viewmodels.auth.FirebaseAuthViewModel


fun NavGraphBuilder.authNav(
    navController: NavHostController
) {
    val authViewModel = FirebaseAuthViewModel()
    navigation(
        startDestination = ScreenRoutes.LoginScreen.route,
        route = ScreenRoutes.AuthNav.route
    ) {
        composable(route = ScreenRoutes.LoginScreen.route) {
            LoginScreen(authViewModel, navController = navController)
        }
        composable(route = ScreenRoutes.SignUpScreen.route) {
            SignUpScreen(authViewModel, navController = navController)
        }

        composable(route = ScreenRoutes.ResetPasswordScreen.route) {
            ResetPasswordScreen(authViewModel, navController = navController)
        }
    }
}
