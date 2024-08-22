package dev.devunion.myportfolio.ui.account

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.devunion.myportfolio.R
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.navigation.ScreenRoutes
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface
import dev.devunion.myportfolio.viewmodels.auth.DummyAuthViewModel

@Preview
@Composable
fun AccountScreenPreview() {
    val authViewModel: DummyAuthViewModel = viewModel()
    val navController = rememberNavController()

    AccountScreen(authViewModel = authViewModel, navController = navController) {}


}

@Composable
fun AccountScreen(
    authViewModel: AuthViewModelInterface,
    navController: NavController,
    logout: () -> Unit
) {
    // Placeholder for user data
    val userName = "John Doe"
    val userEmail = "john.doe@example.com"

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),  // Scrollable in case of more content
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Section
        ProfileSection(userName = userName, userEmail = userEmail)

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
            color = MaterialTheme.colorScheme.onError,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
