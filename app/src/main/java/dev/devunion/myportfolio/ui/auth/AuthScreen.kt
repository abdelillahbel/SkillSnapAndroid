package dev.devunion.myportfolio.ui.auth


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.navigation.Screens
import dev.devunion.myportfolio.ui.main.components.MainScaffold
import dev.devunion.myportfolio.ui.theme.MyPortfolioTheme
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface
import dev.devunion.myportfolio.viewmodels.auth.DummyAuthViewModel

@Preview
@Composable
fun AuthScreenPreview() {
    val authViewModel: DummyAuthViewModel = viewModel()
    val navController = rememberNavController()

    AuthScreen(authViewModel = authViewModel, navController = navController)
}


@Composable
fun AuthScreen(authViewModel: AuthViewModelInterface, navController: NavController) {


    // Check if the user is already logged in
    LaunchedEffect(Unit) {
        authViewModel.isUserLoggedIn(onSuccess = { isLogged ->
            if (isLogged) {
                navController.popBackStack()
                navController.navigate(Screens.MainScreen.route)
            }
        }, onFailure = { exception ->
            Log.w("AuthScreen", "Error: ${exception.message}")
        })
    }
//
//    // Show alert dialog if needed
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            text = { Text(dialogMessage) },
//            confirmButton = {
//                Button(
//                    onClick = { showDialog = false }
//                ) {
//                    Text("Accept")
//                }
//            },
//            modifier = Modifier.animateContentSize()  // Add animation to dialog appearance
//        )
//    }
//
//    // Main content layout
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .imePadding()
//            .padding(16.dp),  // Use padding for a balanced layout
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        // Email Input Field
//        OutlinedTextField(
//            value = authViewModel.email,
//            onValueChange = { authViewModel.email = it },
//            placeholder = { Text(text = "Email") },
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//                .fillMaxWidth()
//                .animateContentSize(),  // Add animation to input field size change
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Password Input Field
//        OutlinedTextField(
//            value = authViewModel.password,
//            onValueChange = { authViewModel.password = it },
//            placeholder = { Text(text = "Password") },
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//                .fillMaxWidth()
//                .animateContentSize(),  // Add animation to input field size change
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Register Button
//        Button(
//            onClick = {
//                authViewModel.register(
//                    onSuccess = {
//                        navController.popBackStack()
//                        navController.navigate(Screens.MainScreen.route) },
//                    onFailure = { exception ->
//                        dialogMessage = exception.message.toString()
//                        showDialog = true
//                    }
//                )
//            },
//            modifier = Modifier
//                .height(60.dp)
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .animateContentSize(),  // Add animation to button size change
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primary
//            ),
//            shape = MaterialTheme.shapes.medium  // Use Material3 shape
//        ) {
//            Text("Register")
//        }
//
//        // Login Button
//        Button(
//            onClick = {
//                authViewModel.login(
//                    onSuccess = {
//                        navController.popBackStack()
//                        navController.navigate(Screens.MainScreen.route) },
//                    onFailure = { exception ->
//                        dialogMessage = exception.message.toString()
//                        showDialog = true
//                    }
//                )
//            },
//            modifier = Modifier
//                .height(60.dp)
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .animateContentSize(),  // Add animation to button size change
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.secondary
//            ),
//            shape = MaterialTheme.shapes.medium  // Use Material3 shape
//        ) {
//            Text("Login")
//        }
//
//        // Recover Password Button
//        Button(
//            onClick = {
//                authViewModel.recoverPassword(
//                    onSuccess = {
//                        dialogMessage = "Password recovered, check your email"
//                        showDialog = true
//                    },
//                    onFailure = { exception ->
//                        dialogMessage = exception.message.toString()
//                        showDialog = true
//                    }
//                )
//            },
//            modifier = Modifier
//                .height(60.dp)
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .animateContentSize(),  // Add animation to button size change
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.tertiary
//            ),
//            shape = MaterialTheme.shapes.medium  // Use Material3 shape
//        ) {
//            Text("Recover Password")
//        }
//    }
}
