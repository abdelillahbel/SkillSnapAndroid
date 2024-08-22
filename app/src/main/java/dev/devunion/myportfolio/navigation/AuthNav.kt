package dev.devunion.myportfolio.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.devunion.myportfolio.ui.auth.login.LoginScreen
import dev.devunion.myportfolio.ui.auth.signup.SignUpScreen
import dev.devunion.myportfolio.viewmodels.auth.FirebaseAuthViewModel


fun NavGraphBuilder.AuthNav(
    navController: NavHostController
) {
    val authViewModel = FirebaseAuthViewModel()
    navigation(
        startDestination = Screens.LoginScreen.route,
        route = Screens.AuthNav.route
    ){
        composable(route = Screens.SignUpScreen.route){
            SignUpScreen(authViewModel,navController = navController)
        }

        composable(route = Screens.LoginScreen.route){
            LoginScreen(authViewModel,navController = navController)
        }
    }
}
