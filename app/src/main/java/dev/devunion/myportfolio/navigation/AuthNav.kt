package dev.devunion.myportfolio.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.devunion.myportfolio.ui.auth.login.LoginScreen
import dev.devunion.myportfolio.ui.auth.reset_password.ResetPasswordScreen
import dev.devunion.myportfolio.ui.auth.signup.SignUpScreen
import dev.devunion.myportfolio.viewmodels.auth.FirebaseAuthViewModel


fun NavGraphBuilder.AuthNav(
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
