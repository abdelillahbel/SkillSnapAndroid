/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.ui.account

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.devunion.myportfolio.R
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.navigation.ScreenRoutes
import dev.devunion.myportfolio.utils.sendMail
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface
import dev.devunion.myportfolio.viewmodels.auth.DummyAuthViewModel
import dev.devunion.myportfolio.viewmodels.auth.FirebaseAuthViewModel

@Preview
@Composable
fun AccountScreenPreview() {
    val authViewModel: FirebaseAuthViewModel = viewModel()
    val navController = rememberNavController()

    AccountScreen(authViewModel = authViewModel, navController = navController) {}

}

@Composable
fun AccountScreen(
    authViewModel: AuthViewModelInterface,
    navController: NavController,
    logout: () -> Unit
) {

    var userId by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Fetch the user data when the composable is launched
    LaunchedEffect(Unit) {
        authViewModel.getUser(
            onSuccess = { user ->
                userId = user.id
                userEmail = user.email
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),  // Scrollable in case of more content
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Section
        ProfileSection(userName = "Email :", userEmail = userEmail)

        Spacer(modifier = Modifier.height(24.dp))

        // Settings Button
        SettingsButton {
            // Navigate to Settings (placeholder function)
//            navController.navigate(ScreenRoutes.SettingsScreen.route)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button with Animation
        LogoutButton(onClick = {
            authViewModel.logout(
                onSuccess = {
                    // logout and forward to auth screen
                    logout.invoke()
                },
                onFailure = { exception ->
                    Toast.makeText(context, exception.message.toString(), Toast.LENGTH_SHORT).show()
                    // Handle error
                }
            )
        })
        Spacer(modifier = Modifier.height(40.dp))
        CopyRightSection()
        Spacer(modifier = Modifier.height(80.dp))
        ContactSupportSection(context = context)
    }
}

@Composable
fun ProfileSection(userName: String, userEmail: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.animateContentSize()  // Smooth transitions for size changes
    ) {
        // Placeholder for profile image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),  // Use actual profile image resource
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)  // Circular profile picture
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // User Name
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        // User Email
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun CopyRightSection() {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally)
                .animateContentSize(),
            text = "Made by DevUnion Foundation",
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ContactSupportSection(context: Context) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    context.sendMail(
                        to = "appshelp@devunion.dev",
                        subject = "Help needed for my portfolio app"
                    )
                }
                .animateContentSize(),
            text = "Contact us at: appshelp@devunion.dev",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun SettingsButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .animateContentSize(),  // Animation for button click
//        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer
//        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("Settings", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .animateContentSize(),  // Animation for button click
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            "Logout",
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
