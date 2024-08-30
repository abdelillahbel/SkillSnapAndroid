/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.devunion.skillsnap.ui.account.AccountScreen
import dev.devunion.skillsnap.ui.home.HomeScreen
import dev.devunion.skillsnap.ui.profile.CreateProfileScreen
import dev.devunion.skillsnap.ui.profile.ProfileScreen
import dev.devunion.skillsnap.ui.profile.UpdateProfileScreen
import dev.devunion.skillsnap.ui.updates.UpdatesScreen
import dev.devunion.skillsnap.viewmodels.auth.FirebaseAuthViewModel
import dev.devunion.skillsnap.viewmodels.db.FirebaseFirestoreViewModel
import dev.devunion.skillsnap.viewmodels.storage.FirebaseStorageViewModel

@Composable
fun MainNavGraph(navController: NavHostController, logout: () -> Unit) {
    // val user = Firebase.auth.currentUser
    // val context = LocalContext.current
    // val preferenceHelper: PreferenceHelper = PreferenceHelper(context)

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
            val storageViewModel = FirebaseStorageViewModel()
            UpdateProfileScreen(
                navController = navController,
                viewModel = firestoreViewModel,
                storageViewModel = storageViewModel
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
