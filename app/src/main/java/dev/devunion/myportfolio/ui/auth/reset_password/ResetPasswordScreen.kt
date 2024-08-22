package dev.devunion.myportfolio.ui.auth.reset_password

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface
import dev.devunion.myportfolio.viewmodels.auth.DummyAuthViewModel

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    val navController = rememberNavController()
    val authViewModel: DummyAuthViewModel = viewModel()
    ResetPasswordScreen(authViewModel = authViewModel, navController = navController)
}

@Composable
fun ResetPasswordScreen(authViewModel: AuthViewModelInterface, navController: NavController) {

}
