/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.profile

import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.models.Education
import dev.devunion.skillsnap.models.Experience
import dev.devunion.skillsnap.models.Project
import dev.devunion.skillsnap.models.UserInfo
import dev.devunion.skillsnap.navigation.ScreenRoutes
import dev.devunion.skillsnap.viewmodels.db.FirestoreViewModelInterface

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: FirestoreViewModelInterface
) {
    var showEditExperienceDialog by remember { mutableStateOf<Pair<String, Experience>?>(null) }
    val user = Firebase.auth.currentUser
    val currentUserId = user!!.uid
    var showDialog by remember { mutableStateOf(false) }
    var userInfo by remember { mutableStateOf<UserInfo?>(null) }
    var hasProfile by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val poppinsFamily = FontFamily(
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )


    // Log.i("TAG", "ProfileScreen: ${currentUserId}")

    // Check if the user has a profile
    LaunchedEffect(Unit) {
        viewModel.checkUserHasProfile(
            userId = currentUserId,
            onSuccess = { profileExists ->
                hasProfile = profileExists
                if (profileExists) {
                    // Fetch the profile data
                    viewModel.fetchUserProfile(
                        userId = currentUserId,
                        onSuccess = { fetchedUserInfo ->
                            userInfo = fetchedUserInfo
                            isLoading = false // Stop loading after data is fetched
                        },
                        onFailure = { exception ->
                            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                            isLoading = false // Stop loading even if an error occurs
                        }
                    )
                } else {
                    isLoading = false // Stop loading if no profile exists
                }
            },
            onFailure = { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                isLoading = false // Stop loading if an error occurs
            }
        )
    }

    if (isLoading) {
        // Show loading indicator in the center of the screen
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .animateContentSize()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {


            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium
            )
            when {

                hasProfile -> {
                    userInfo?.let { user ->
                        // Display user general info

                        val profilePageUrl = "https://skill-snap-web.vercel.app/${user.username}"


                        Card(
                            modifier = Modifier
                                .animateContentSize()
                                .fillMaxWidth()
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                                    .padding(16.dp, 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    modifier = Modifier
                                        .animateContentSize()
                                        .padding(start = 2.dp, end = 4.dp)
                                        .align(Alignment.CenterVertically),
                                    text = "Your portfolio is live now 🎉",
                                    style = MaterialTheme.typography.titleSmall,
                                    textAlign = TextAlign.Start,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Button(onClick = {
                                    CustomTabsIntent
                                        .Builder()
                                        .build()
                                        .launchUrl(context, Uri.parse(profilePageUrl))
                                }) {
                                    Text("Open")
                                }
                            }
                        }





                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(16.dp, 8.dp),
                                text = "Name: ${user.name}",
                                style = MaterialTheme.typography.headlineSmall
                            )

                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(16.dp, 8.dp),
                                text = "Status: ${user.status}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(16.dp, 8.dp),
                                text = "Role: ${user.role}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(16.dp, 8.dp),
                                text = "Bio: ${user.bio}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(16.dp, 8.dp),
                                text = "About: ${user.about}",
                                style = MaterialTheme.typography.bodyLarge
                            )

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Projects section
                        ExpandableSection(
                            title = "Projects",
                            content = {
                                user.projects.forEach { (id, project) ->
                                    ProjectItem(
                                        project = project,
                                        onUpdate = { updatedProject ->
                                            val updatedProjects = user.projects.toMutableMap()
                                            updatedProjects[id] = updatedProject
                                            viewModel.saveUserInfo(
                                                user.copy(projects = updatedProjects),
                                                onSuccess = {
                                                    // Handle success
                                                },
                                                onFailure = { exception ->
                                                    // Handle failure
                                                })
                                        }
                                    )
                                }
                            }
                        )

                        // Education section
                        ExpandableSection(
                            title = "Education",
                            content = {
                                user.education.forEach { (id, education) ->
                                    EducationItem(
                                        education = education,
                                        onUpdate = { updatedEducation ->
                                            val updatedEducationMap = user.education.toMutableMap()
                                            updatedEducationMap[id] = updatedEducation
                                            viewModel.saveUserInfo(
                                                user.copy(education = updatedEducationMap),
                                                onSuccess = {
                                                    // Handle success
                                                },
                                                onFailure = { exception ->
                                                    // Handle failure
                                                })
                                        }
                                    )
                                }
                            }
                        )

                        // Experience section
                        ExpandableSection(
                            title = "Experience",
                            content = {
                                user.experience.forEach { (id, experience) ->
                                    ExperienceItem(
                                        experience = experience,
                                        onUpdate = { updatedExperience ->
                                            val updatedExperienceMap =
                                                user.experience.toMutableMap()
                                            updatedExperienceMap[id] = updatedExperience
                                            viewModel.saveUserInfo(
                                                user.copy(experience = updatedExperienceMap),
                                                onSuccess = {
                                                    // Handle success
                                                },
                                                onFailure = { exception ->
                                                    // Handle failure
                                                })
                                        }
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(onClick = {
                            viewModel.saveUserInfo(user, onSuccess = {
                                // Handle success
                            }, onFailure = { exception ->
                                // Handle failure
                            })
                        }) {
                            Text("Save Changes")
                        }

                        Button(onClick = {
                            navController.navigate(ScreenRoutes.UpdateProfileScreen.route)
                        }) {
                            Text("Update portfolio")
                        }

                        Button(onClick = { showDialog = true }) {
                            Text("Delete portfolio")
                        }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Delete portfolio") },
                                text = { Text("Dear, everything is under your control, so are you sure you want to delete your profile? This action cannot be undone!") },
                                confirmButton = {
                                    Button(onClick = {
                                        showDialog = false
                                        viewModel.deleteUserProfile(
                                            userId = currentUserId,
                                            username = userInfo?.username.orEmpty(),
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    "Profile deleted successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                // Navigate to home
                                                navController.navigate(ScreenRoutes.HomeScreen.route)
                                                // Handle additional actions after deletion, like navigation
                                            },
                                            onFailure = { exception ->
                                                Toast.makeText(
                                                    context,
                                                    "Failed to delete profile: ${exception.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }) {
                                        Text("Confirm")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = { showDialog = false }) {
                                        Text("Cancel")
                                    }
                                },
                                modifier = Modifier.animateContentSize()
                            )
                        }


                        // Show edit dialog if needed
                        showEditExperienceDialog?.let { (id, experience) ->
                            EditExperienceDialog(
                                experience = experience,
                                onDismiss = { showEditExperienceDialog = null },
                                onSave = { updatedExperience ->
                                    val updatedExperienceMap = user.experience.toMutableMap()
                                    updatedExperienceMap[id] = updatedExperience
                                    viewModel.saveUserInfo(
                                        user.copy(experience = updatedExperienceMap),
                                        onSuccess = {
                                            // Handle success
                                        },
                                        onFailure = { exception ->
                                            // Handle failure
                                        })
                                }
                            )
                        }
                    }
                }

                else -> {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = "It looks like you don't have portfolio published yet, let's create one."
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            navController.navigate(ScreenRoutes.CreateProfileScreen.route)
                        }) {
                            Text("Create Portfolio")
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun ExpandableSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun ProjectItem(
    project: Project,
    onUpdate: (Project) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = project.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = project.description, style = MaterialTheme.typography.bodyMedium)
        if (project.image != null) {
            Image(
                painter = rememberAsyncImagePainter(project.image),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }) {
            Text("Edit")
        }

        if (showDialog) {
            EditProjectDialog(project = project, onUpdate = {
                onUpdate(it)
                showDialog = false
            }, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun EducationItem(
    education: Education,
    onUpdate: (Education) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "${education.degree} from ${education.institution}",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "Year: ${education.year}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }) {
            Text("Edit")
        }

        if (showDialog) {
            EditEducationDialog(education = education, onUpdate = {
                onUpdate(it)
                showDialog = false
            }, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun EditProjectDialog(
    project: Project,
    onUpdate: (Project) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(project.title) }
    var description by remember { mutableStateOf(project.description) }
    var image by remember { mutableStateOf(project.image) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Project") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                // Add Image picker if needed
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdate(project.copy(title = title, description = description, image = image))
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = Modifier.animateContentSize()
    )
}

@Composable
fun EditEducationDialog(
    education: Education,
    onUpdate: (Education) -> Unit,
    onDismiss: () -> Unit
) {
    var degree by remember { mutableStateOf(education.degree) }
    var institution by remember { mutableStateOf(education.institution) }
    var year by remember { mutableStateOf(education.year) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Education") },
        text = {
            Column {
                OutlinedTextField(
                    value = degree,
                    onValueChange = { degree = it },
                    label = { Text("Degree") }
                )
                OutlinedTextField(
                    value = institution,
                    onValueChange = { institution = it },
                    label = { Text("Institution") }
                )
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Year") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdate(
                        education.copy(
                            degree = degree,
                            institution = institution,
                            year = year
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = Modifier.animateContentSize()
    )
}

@Composable
fun ExperienceItem(
    experience: Experience,
    onUpdate: (Experience) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var updatedExperience by remember { mutableStateOf(experience) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = experience.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = experience.company, style = MaterialTheme.typography.displayMedium)
        Text(text = experience.period, style = MaterialTheme.typography.bodySmall)
        Text(text = experience.description, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { isEditing = true }) {
            Text("Edit")
        }

        if (isEditing) {
            EditExperienceDialog(
                experience = updatedExperience,
                onDismiss = { isEditing = false },
                onSave = { editedExperience ->
                    updatedExperience = editedExperience
                    onUpdate(updatedExperience)
                    isEditing = false
                }
            )
        }
    }
}

@Composable
fun EditExperienceDialog(
    experience: Experience,
    onDismiss: () -> Unit,
    onSave: (Experience) -> Unit
) {
    var title by remember { mutableStateOf(experience.title) }
    var company by remember { mutableStateOf(experience.company) }
    var period by remember { mutableStateOf(experience.period) }
    var description by remember { mutableStateOf(experience.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Experience") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Company") }
                )
                TextField(
                    value = period,
                    onValueChange = { period = it },
                    label = { Text("Period") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        Experience(
                            title = title,
                            company = company,
                            period = period,
                            description = description
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = Modifier.animateContentSize()
    )
}
