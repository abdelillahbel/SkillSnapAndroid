/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

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
import dev.devunion.myportfolio.ui.profile.CreateProfileScreen
import dev.devunion.myportfolio.ui.profile.ProfileScreen
import dev.devunion.myportfolio.ui.profile.UpdateProfileScreen
import dev.devunion.myportfolio.ui.splash.SplashScreen
import dev.devunion.myportfolio.ui.updates.UpdatesScreen
import dev.devunion.myportfolio.utils.PreferenceHelper
import dev.devunion.myportfolio.viewmodels.auth.FirebaseAuthViewModel
import dev.devunion.myportfolio.viewmodels.db.FirebaseFirestoreViewModel

@Composable
fun MainNavGraph(navController: NavHostController, logout: () -> Unit) {
    val user = Firebase.auth.currentUser
    val context = LocalContext.current
    val preferenceHelper: PreferenceHelper = PreferenceHelper(context)

    NavHost(
        navController = navController,
        route = ScreenRoutes.MainNav.route,
        startDestination = ScreenRoutes.HomeScreen.route


    ) {

        composable(route = ScreenRoutes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = ScreenRoutes.ProfileScreen.route) {
            val firestoreViewModel = FirebaseFirestoreViewModel()
            ProfileScreen(navController = navController, firestoreViewModel)
        }

        composable(route = ScreenRoutes.CreateProfileScreen.route) {
            val firestoreViewModel = FirebaseFirestoreViewModel()
            CreateProfileScreen(
                navController = navController,
                viewModel = firestoreViewModel,
                onProfileCreated = {
                    navController.popBackStack() // Navigate back to profile screen
                })
        }

        composable(route = ScreenRoutes.UpdateProfileScreen.route) {
            val firestoreViewModel = FirebaseFirestoreViewModel()
            UpdateProfileScreen(
                navController = navController,
                viewModel = firestoreViewModel
            )
        }

        composable(route = ScreenRoutes.UpdatesScreen.route) {
            UpdatesScreen(navController)
        }
        composable(route = ScreenRoutes.AccountScreen.route) {
            val authViewModel = FirebaseAuthViewModel()
            AccountScreen(authViewModel, navController = navController, logout)
        }


        /*   composable(route = ScreenRoutes.OnboardingScreen.route) {
               OnboardingScreen {
                   navController.popBackStack()
                   navController.navigate(route = ScreenRoutes.AuthScreen.route)
               }
           }

           composable(route = ScreenRoutes.AuthScreen.route, enterTransition = {
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
                   navController.navigate(route = ScreenRoutes.MainScreen.route)
               })
           }*/


    }
}

//@Composable
//fun BottomNavGraph(
//    navController: NavHostController
//) {
//
//    NavHost(
//        navController = navController,
//        startDestination = ScreenRoutes.HomeScreen.route
//    ) {
//        composable(route = ScreenRoutes.HomeScreen.route) {
//            HomeScreen(navController)
//        }
//        composable(route = ScreenRoutes.ProfileScreen.route) {
//            ProfileScreen(navController)
//        }
//        composable(route = ScreenRoutes.UpdatesScreen.route) {
//            UpdatesScreen(navController)
//        }
//        composable(route = ScreenRoutes.AccountScreen.route) {
//            val authViewModel = FirebaseAuthViewModel()
//            AccountScreen(authViewModel, navController)
//        }
//    }
//}
