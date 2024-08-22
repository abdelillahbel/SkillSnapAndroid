package dev.devunion.myportfolio.navigation


sealed class Screens(val route: String) {
//    object OnboardingScreen : Screens("onboarding_screen")
//
//    // Onboarding screens
//   data object OnboardingScreen1 : Screens("onboarding_screen_1")
//   data object OnboardingScreen2 : Screens("onboarding_screen_2")
//   data object OnboardingScreen3 : Screens("onboarding_screen_3")
//   data object OnboardingScreen4 : Screens("onboarding_screen_4")

    // App screens
    data object SplashScreen : Screens("splash_screen")

    data object MainScreen : Screens("main_screen")

    data object LoginScreen : Screens("login_screen")
    data object SignUpScreen : Screens("signup_screen")
    data object ResetPasswordScreen : Screens("reset_password_screen")

    data object HomeScreen : Screens("home_screen")
    data object ProfileScreen : Screens("profile_screen")
    data object UpdatesScreen : Screens("updates_screen")
    data object AccountScreen : Screens("account_screen")

    //Graph Routes
    data object AuthNav : Screens("AUTH_NAV_GRAPH")

    data object MainNav : Screens("MAIN_NAV_GRAPH")
}
