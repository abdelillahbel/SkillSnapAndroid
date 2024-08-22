package dev.devunion.myportfolio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.myportfolio.ui.account.AccountScreen
import dev.devunion.myportfolio.ui.auth.login.LoginScreen
import dev.devunion.myportfolio.ui.auth.reset_password.ResetPasswordScreen
import dev.devunion.myportfolio.ui.auth.signup.SignUpScreen
import dev.devunion.myportfolio.ui.home.HomeScreen
import dev.devunion.myportfolio.ui.main.MainScreen
import dev.devunion.myportfolio.ui.profile.ProfileScreen
import dev.devunion.myportfolio.ui.splash.SplashScreen
import dev.devunion.myportfolio.ui.updates.UpdatesScreen
import dev.devunion.myportfolio.viewmodels.auth.FirebaseAuthViewModel

@Composable
fun MainNavGraph(navController: NavHostController) {
    val user = Firebase.auth.currentUser
    val context = LocalContext.current
    // val preferenceHelper:  PreferenceHelper = PreferenceHelper(context)

    NavHost(
        navController = navController,
        route = Screens.MainNav.route,
        startDestination = when (user) {
            null -> Screens.LoginScreen.route
            else -> Screens.MainScreen.route
        }

    ) {

        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = Screens.LoginScreen.route) {
            val authViewModel = FirebaseAuthViewModel()
            LoginScreen(authViewModel, navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            val authViewModel = FirebaseAuthViewModel()
            SignUpScreen(authViewModel, navController)
        }
        composable(route = Screens.ResetPasswordScreen.route) {
            val authViewModel = FirebaseAuthViewModel()
            ResetPasswordScreen(authViewModel, navController)
        }


        /*   composable(route = Screens.OnboardingScreen.route) {
               OnboardingScreen {
                   navController.popBackStack()
                   navController.navigate(route = Screens.AuthScreen.route)
               }
           }

           composable(route = Screens.AuthScreen.route, enterTransition = {
               slideIntoContainer(
                   AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700)
               )
           }, exitTransition = {
               slideOutOfContainer(
                   AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700)
               )
           }) {
               AuthScreen(navigateToMainScreen = {
                   navController.popBackStack()
                   navController.navigate(route = Screens.MainScreen.route)
               })
           }*/


    }
}

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(route = Screens.UpdatesScreen.route) {
            UpdatesScreen(navController)
        }
        composable(route = Screens.AccountScreen.route) {
            val authViewModel = FirebaseAuthViewModel()
            AccountScreen(authViewModel, navController)
        }
    }
}
