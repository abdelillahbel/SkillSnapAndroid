/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.skillsnap.utils.PreferenceHelper
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.navigation.ScreenRoutes


@Preview
@Composable
private fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(
        navController = navController,
        navigateToAuthNav = ({}),
        navigateToMainNav = ({ })
    )
}

@Composable
fun SplashScreen(
    navController: NavController,
    navigateToMainNav: () -> Unit,
    navigateToAuthNav: () -> Unit
) {
    val context = LocalContext.current
    val user = Firebase.auth.currentUser
//    val preferenceHelper: PreferenceHelper = PreferenceHelper(context)

    LaunchedEffect(Unit) {
//        delay(2000)

        when (user) {
            null -> navigateToAuthNav.invoke()
            else -> navigateToMainNav.invoke()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(150.dp),
            painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = "splash logo"
        )
    }
}
