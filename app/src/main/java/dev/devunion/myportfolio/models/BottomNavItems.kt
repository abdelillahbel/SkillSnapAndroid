package dev.devunion.myportfolio.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import dev.devunion.myportfolio.R
import dev.devunion.myportfolio.navigation.ScreenRoutes

sealed class BottomNavItems(
    val title: String,
    val route: String,
    val icon: ImageVector
) {
    data object HomeItem : BottomNavItems(
        title = "Home",
        route = ScreenRoutes.HomeScreen.route,
        icon = Icons.Outlined.Home,
    )

    data object Profile : BottomNavItems(
        title = "Profile",
        route = ScreenRoutes.AccountScreen.route,
        icon = Icons.Outlined.Home
    )
}
