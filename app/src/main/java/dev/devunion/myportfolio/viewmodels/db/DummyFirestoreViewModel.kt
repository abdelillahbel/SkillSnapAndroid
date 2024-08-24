package dev.devunion.myportfolio.viewmodels.db

import androidx.lifecycle.ViewModel
import dev.devunion.myportfolio.models.Contact
import dev.devunion.myportfolio.models.Education
import dev.devunion.myportfolio.models.Experience
import dev.devunion.myportfolio.models.Project
import dev.devunion.myportfolio.models.UserInfo


class DummyFirestoreViewModel : FirestoreViewModelInterface, ViewModel() {

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
            createdAt = System.currentTimeMillis()
        )
        onSuccess(dummyUserInfo)
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
                createdAt = System.currentTimeMillis()
            )
        )
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
