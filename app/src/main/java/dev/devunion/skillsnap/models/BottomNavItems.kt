/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.navigation.ScreenRoutes

/* TODO */
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
