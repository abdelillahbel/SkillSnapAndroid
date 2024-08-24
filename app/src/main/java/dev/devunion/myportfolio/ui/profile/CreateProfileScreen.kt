package dev.devunion.myportfolio.ui.profile


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.myportfolio.models.Contact
import dev.devunion.myportfolio.models.UserInfo
import dev.devunion.myportfolio.viewmodels.db.FirestoreViewModelInterface


@Composable
fun CreateProfileScreen(
    navController: NavController,
    viewModel: FirestoreViewModelInterface, // Injected ViewModel
    onProfileCreated: () -> Unit // Callback when profile is successfully created
) {

    val user = Firebase.auth.currentUser
    val currentUserId = user!!.uid

    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var avatar by remember { mutableStateOf("") }
    var resume by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        TextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") }
        )

        TextField(
            value = about,
            onValueChange = { about = it },
            label = { Text("About") }
        )

        TextField(
            value = avatar,
            onValueChange = { avatar = it },
            label = { Text("Avatar URL") }
        )

        TextField(
            value = resume,
            onValueChange = { resume = it },
            label = { Text("Resume URL") }
        )

        Button(
            onClick = {
                if (username.isNotBlank() && name.isNotBlank() && bio.isNotBlank()) {
                    // Proceed with profile creation

                    // Create the UserInfo object
                    val userInfo = UserInfo(
                        username = username,
                        id = currentUserId,
                        name = name,
                        status = "active",
                        role = "user",
                        bio = bio,
                        avatar = avatar,
                        about = about,
                        education = emptyMap(), // Assuming these will be filled later
                        experience = emptyMap(),
                        projects = emptyMap(),
                        contact = Contact(email = user.email.toString(), phone = ""),
                        resume = resume,
                        createdAt = Timestamp.now(),
                    )

                    // Save user info to Firestore
                    viewModel.saveUserInfo(
                        userInfo = userInfo,
                        onSuccess = {
                            // Set hasProfile flag to true in the users collection
                            viewModel.updateHasProfileFlag(
                                userId = currentUserId,
                                hasProfile = true,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Profile of $username created successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onProfileCreated() // Navigate back or to another screen
                                },
                                onFailure = { exception ->
                                    Toast.makeText(
                                        context,
                                        "Error updating hasProfile flag: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        },
                        onFailure = { exception ->
                            Toast.makeText(
                                context,
                                "Error saving profile: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )



                } else {
                    Toast.makeText(
                        context,
                        "Please fill in all required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Profile")
        }
    }
}
