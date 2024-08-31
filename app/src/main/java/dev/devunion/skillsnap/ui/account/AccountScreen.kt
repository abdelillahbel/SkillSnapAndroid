/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.account

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.models.User
import dev.devunion.skillsnap.utils.sendMail
import dev.devunion.skillsnap.viewmodels.auth.AuthViewModelInterface
import dev.devunion.skillsnap.viewmodels.auth.FirebaseAuthViewModel

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

    var user by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val poppinsFamily = FontFamily(
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )

    // Fetch the user data when the composable is launched
    LaunchedEffect(Unit) {
        authViewModel.getUserData(
            onSuccess = { fetchedUser ->
                user = fetchedUser
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),  // Scrollable in case of more content
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Account",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )



        when {
            user != null -> {
                ProfileSection(userName = user!!.name, userEmail = user!!.email)

                Spacer(modifier = Modifier.height(24.dp))

                // Settings Button
                SettingsButton {
                    // Navigate to Settings (placeholder function)
                    // navController.navigate(ScreenRoutes.SettingsScreen.route)
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
                            Toast.makeText(
                                context,
                                exception.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                })
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "An error occurred",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                CircularProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(150.dp))

        ContactAndCopyrightSection()

        Spacer(modifier = Modifier.height(80.dp))
    }
}


@Composable
fun ProfileSection(userName: String?, userEmail: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.animateContentSize()  // Smooth transitions for size changes
    ) {
        // Placeholder for profile image
        Image(
            painter = rememberAsyncImagePainter(""),  // Use actual profile image resource
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)  // Circular profile picture
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // User Name
        Text(
            text = userName ?: "No Name Provided",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        // User Email
        Text(
            text = userEmail ?: "No Email Provided",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun ContactAndCopyrightSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),  // Smooth transitions for size changes
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        // Contact Section with Animation
        AnimatedVisibility(visible = true) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "contact us",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                ContactRow("\uD835\uDD4F ", "devunionorg") {
                    CustomTabsIntent
                        .Builder()
                        .build()
                        .launchUrl(context, Uri.parse("https://x.com/devunionorg"))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Copyright Section with Animation
        AnimatedVisibility(visible = true) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "© 2024 SkillSnap",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "All rights reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun ContactRow(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        TextButton(
            onClick = { onClick() },

            ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
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
