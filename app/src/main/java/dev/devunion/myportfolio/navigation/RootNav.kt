package dev.devunion.myportfolio.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.myportfolio.ui.main.MainScreen


@Composable
fun RootNav(navController: NavHostController) {
    val user = Firebase.auth.currentUser
    NavHost(
        navController = navController,
        startDestination = when (user) {
            null -> ScreenRoutes.AuthNav.route
            else -> ScreenRoutes.MainNav.route
        }
    ) {

        AuthNav(navController)

        composable(route = ScreenRoutes.MainNav.route) {
            MainScreen(navController,
                logout = {
                    navController.navigate(ScreenRoutes.AuthNav.route) {
                        popUpTo(0) {}
                    }
                }
            )
        }
    }
}
