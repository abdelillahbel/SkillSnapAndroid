package dev.devunion.myportfolio

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.navigation.MainNavGraph
import dev.devunion.myportfolio.navigation.RootNav
import dev.devunion.myportfolio.navigation.Screens

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
            val currentApiVersion = Build.VERSION.SDK_INT
            if (currentApiVersion < Build.VERSION_CODES.S) {
                navigateToSplashScreen()
            }
        }
    }

    private fun navigateToSplashScreen() {
        navController.popBackStack()
        navController.navigate(route = Screens.SplashScreen.route)
    }


    private fun navigateToMainScreen() {
        navController.popBackStack()
        navController.navigate(route = Screens.MainScreen.route)
    }

    private fun navigateToLoginScreen() {
        navController.popBackStack()
        navController.navigate(route = Screens.LoginScreen.route)
    }
}
