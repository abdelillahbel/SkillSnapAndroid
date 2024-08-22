package dev.devunion.myportfolio.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.ui.main.MainScreen


@Composable
fun RootNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainNav.route
    ) {

        AuthNav(navController)
        composable(route = Screens.MainNav.route) {
            MainNavGraph(navController = navController)
        }

//        composable(route = Screens.MainNav.route){
//            MainScreen(navController
//                logout = {
//                    navController.navigate(Screens.AuthNav.route) {
//                        popUpTo(0){}
//                    }
//                }
//            )
//        }
    }
}
