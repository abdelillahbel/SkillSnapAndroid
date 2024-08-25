/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Portrait
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.devunion.myportfolio.navigation.ScreenRoutes

@Composable
fun MainBottomBar(navController: NavHostController) {

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {

            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            NavBarItems.BarItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.name,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = navItem.name,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }

        }
    }
}

object NavBarItems {

    val BarItems = listOf(

        BottomBarItem(
            icon = Icons.Outlined.Home,
            name = "Home",
            route = ScreenRoutes.HomeScreen.route,
        ), BottomBarItem(
            icon = Icons.Outlined.Portrait,
            name = "Profile",
            route = ScreenRoutes.ProfileScreen.route
        ), BottomBarItem(
            icon = Icons.Outlined.TipsAndUpdates,
            name = "Updates",
            route = ScreenRoutes.UpdatesScreen.route
        ), BottomBarItem(
            icon = Icons.Outlined.AccountCircle,
            name = "Account",
            route = ScreenRoutes.AccountScreen.route
        )
    )
}

data class BottomBarItem(
    val icon: ImageVector,
    val name: String,
    val route: String
)
