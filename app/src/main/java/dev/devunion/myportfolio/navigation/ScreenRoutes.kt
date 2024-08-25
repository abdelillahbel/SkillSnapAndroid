/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.navigation


sealed class ScreenRoutes(val route: String) {
//    object OnboardingScreen : ScreenRoutes("onboarding_screen")
//
//    // Onboarding screens
//   data object OnboardingScreen1 : ScreenRoutes("onboarding_screen_1")
//   data object OnboardingScreen2 : ScreenRoutes("onboarding_screen_2")
//   data object OnboardingScreen3 : ScreenRoutes("onboarding_screen_3")
//   data object OnboardingScreen4 : ScreenRoutes("onboarding_screen_4")

    // App screens
    data object SplashScreen : ScreenRoutes("splash_screen")

    data object MainScreen : ScreenRoutes("main_screen")

    data object LoginScreen : ScreenRoutes("login_screen")
    data object SignUpScreen : ScreenRoutes("signup_screen")
    data object ResetPasswordScreen : ScreenRoutes("reset_password_screen")

    data object HomeScreen : ScreenRoutes("home_screen")
    data object ProfileScreen : ScreenRoutes("profile_screen")
    data object CreateProfileScreen : ScreenRoutes("create_profile_screen")
    data object UpdateProfileScreen : ScreenRoutes("update_profile_screen")

    data object UpdatesScreen : ScreenRoutes("updates_screen")
    data object AccountScreen : ScreenRoutes("account_screen")

    //Graph Routes
    data object AuthNav : ScreenRoutes("AUTH_NAV_GRAPH")

    data object MainNav : ScreenRoutes("MAIN_NAV_GRAPH")
}
