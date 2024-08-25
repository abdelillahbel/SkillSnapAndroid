/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.ui.main.components

import androidx.compose.material3.*
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.navigation.ScreenRoutes
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface
import dev.devunion.myportfolio.viewmodels.auth.DummyAuthViewModel

@Preview
@Composable
fun HtpPre() {
    val authViewModel: DummyAuthViewModel = viewModel()
    val navController = rememberNavController()
    MainTopAppBar(authViewModel,navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(authViewModel: AuthViewModelInterface, navController: NavController
                  ) {
    TopAppBar(
        title = { Text(text = "Home") },
        actions = {
            TextButton(onClick = {
                authViewModel.logout(onSuccess = {
                    navController.navigate(ScreenRoutes.LoginScreen.route)
                }, onFailure = { exception ->
//                    logViewModel.crash(screenName, exception)
                })
            }) {
                Text(
                    text = "Logout",
                )
            }
        }
    )
}
