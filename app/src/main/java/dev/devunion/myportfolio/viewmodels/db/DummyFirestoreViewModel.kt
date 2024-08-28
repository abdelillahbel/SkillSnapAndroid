/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.myportfolio.viewmodels.db

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import dev.devunion.myportfolio.models.Contact
import dev.devunion.myportfolio.models.Education
import dev.devunion.myportfolio.models.Experience
import dev.devunion.myportfolio.models.Project
import dev.devunion.myportfolio.models.UserInfo


class DummyFirestoreViewModel : FirestoreViewModelInterface, ViewModel() {


    private val existingUsernames = setOf("johnDoe", "janeSmith", "devUser123")

    override fun isUsernameAvailable(
        username: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate a delay to mimic network or database query time
        Handler(Looper.getMainLooper()).postDelayed({
            if (existingUsernames.contains(username)) {
                // Username is already taken
                onSuccess(false)
            } else {
                // Username is available
                onSuccess(true)
            }
        }, 1000) // 1-second delay
    }


    // Simulate a data source
    private val mockUserProfiles = mapOf(
        "user123" to UserInfo(
            username = "john_doe",
            name = "John Doe",
            bio = "Android Developer",
            avatar = "",
            resume = "",
            role = "Developer",
            about = "Loves coding",
            education = mutableMapOf(
                "edu1" to Education(
                    "Bachelor's in CS",
                    "University X",
                    "2020"
                )
            ),
            experience = mutableMapOf(
                "exp1" to Experience(
                    "Android Developer",
                    "Company Y",
                    "2 years"
                )
            ),
            projects = mutableMapOf("proj1" to Project("Chat App", "Messaging app", "2023")),
            contact = Contact("john.doe@example.com", "01244848566"),
            createdAt = Timestamp.now(),
            id = "user123"
        ),
        "user456" to UserInfo(
            username = "jane_smith",
            name = "Jane Smith",
            bio = "Full-Stack Developer",
            avatar = "",
            resume = "",
            role = "Developer",
            about = "Building end-to-end solutions",
            education = mutableMapOf("edu1" to Education("Master's in IT", "University Y", "2021")),
            experience = mutableMapOf(
                "exp1" to Experience(
                    "Full-Stack Developer",
                    "Company Z",
                    "3 years"
                )
            ),
            projects = mutableMapOf("proj1" to Project("E-commerce App", "Shopping app", "2022")),
            contact = Contact("jane_smith@example.com", "01244848566"),
            createdAt = Timestamp.now(),
            id = "user456"
        )
    )

    override fun fetchUsernameByUserId(
        userId: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate fetching from Firestore
        val matchingProfile = mockUserProfiles.values.find { it.id == userId }
        if (matchingProfile != null) {
            onSuccess(matchingProfile.username)
        } else {
            onFailure(Exception("User ID not found"))
        }
    }

    override fun checkUserHasProfile(
        userId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate a check, always returns true for testing purposes
        onSuccess(true)
    }

    override fun fetchUserProfile(
        userId: String,
        onSuccess: (UserInfo) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Provide a dummy user profile for testing
        val dummyUserInfo = UserInfo(
            username = "dummyUser",
            id = userId,
            name = "Dummy User",
            status = "visitor",
            role = "Tester",
            bio = "This is a dummy bio for testing.",
            avatar = "https://dummyimage.com/100",
            about = "This is a dummy about section.",
            education = emptyMap(),
            experience = emptyMap(),
            projects = emptyMap(),
            contact = Contact(email = "dummy@example.com", phone = "123456789"),
            resume = "dummyResumeUrl",
            createdAt = Timestamp.now(),
        )
        onSuccess(dummyUserInfo)
    }

    override fun updateHasProfileFlag(
        userId: String,
        hasProfile: Boolean,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate success or failure for testing purposes
        val isSuccess = true // Set to false to simulate a failure

        if (isSuccess) {
            // Simulate a successful update
            onSuccess()
        } else {
            // Simulate a failure with an exception
            onFailure(Exception("Failed to update hasProfile flag in dummy ViewModel"))
        }
    }


    override fun fetchUserInfo(
        username: String,
        onSuccess: (UserInfo) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate fetching user info, return dummy data
        onSuccess(
            UserInfo(
                username = username,
                id = "dummyId",
                name = "Dummy Name",
                status = "visitor",
                role = "Dummy Role",
                bio = "This is a dummy bio",
                avatar = "https://dummyimage.com/100",
                about = "About Dummy",
                education = emptyMap(),
                experience = emptyMap(),
                projects = emptyMap(),
                contact = Contact("dummy@example.com", "1234567890"),
                resume = "dummyResume",
                createdAt = Timestamp.now(),
            )
        )
    }

    override fun deleteUserProfile(
        userId: String,
        username: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate profile deletion
        onSuccess()
    }

    override fun saveUserInfo(
        userInfo: UserInfo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simulate a delay for testing
        try {
            // Assuming saving is always successful in the dummy implementation
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
