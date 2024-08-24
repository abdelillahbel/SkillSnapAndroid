package dev.devunion.myportfolio.viewmodels.db

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dev.devunion.myportfolio.models.UserInfo


class FirebaseFirestoreViewModel : FirestoreViewModelInterface, ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    override fun updateHasProfileFlag(
        userId: String,
        hasProfile: Boolean,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userDocRef = firestore.collection("users").document(userId)

        userDocRef.update("hasProfile", hasProfile)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }



    override fun deleteUserProfile(
        userId: String,
        username: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userProfilesRef = firestore.collection("userProfiles").document(username)
        val userRef = firestore.collection("users").document(userId)

        firestore.runBatch { batch ->
            batch.delete(userProfilesRef)
            batch.update(userRef, "hasProfile", false)
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener(onFailure)
    }

    override fun fetchUsernameByUserId(
        userId: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()

        db.collection("userProfiles")
            .whereEqualTo("id", userId)
            .limit(1) // Assuming each user has a unique ID
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val username = document.id
                    onSuccess(username)
                } else {
                    onFailure(Exception("No user profile found for this ID"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    override fun checkUserHasProfile(
        userId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val hasProfile = document.getBoolean("hasProfile") ?: false
                    onSuccess(hasProfile)
                } else {
                    onSuccess(false)
                }
            }
            .addOnFailureListener(onFailure)
    }

    override fun fetchUserProfile(
        userId: String,
        onSuccess: (UserInfo) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("userProfiles").whereEqualTo("id", userId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userInfo = documents.documents[0].toObject(UserInfo::class.java)
                    if (userInfo != null) {
                        onSuccess(userInfo)
                    } else {
                        onFailure(Exception("Profile is empty"))
                    }
                } else {
                    onFailure(Exception("Profile not found"))
                }
            }
            .addOnFailureListener(onFailure)
    }

    override fun fetchUserInfo(
        username: String,
        onSuccess: (UserInfo) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("userProfiles").document(username)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userInfo = document.toObject(UserInfo::class.java)
                    if (userInfo != null) {
                        onSuccess(userInfo)
                    } else {
                        onFailure(Exception("User info is empty"))
                    }
                } else {
                    onFailure(Exception("User info not found"))
                }
            }
            .addOnFailureListener(onFailure)
    }

    override fun saveUserInfo(
        userInfo: UserInfo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("userProfiles").document(userInfo.username)
            .set(userInfo)
            .addOnSuccessListener {
                // Update the hasProfile field in the users collection
                firestore.collection("users").document(userInfo.id)
                    .update("hasProfile", true)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener(onFailure)
            }
            .addOnFailureListener(onFailure)
    }
}
