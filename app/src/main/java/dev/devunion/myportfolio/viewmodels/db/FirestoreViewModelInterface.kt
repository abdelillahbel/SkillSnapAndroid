package dev.devunion.myportfolio.viewmodels.db

import dev.devunion.myportfolio.models.UserInfo


interface FirestoreViewModelInterface {
    fun checkUserHasProfile(
        userId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun fetchUserProfile(
        userId: String,  // This is the new method we're adding
        onSuccess: (UserInfo) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun fetchUserInfo(
        username: String,
        onSuccess: (UserInfo) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun saveUserInfo(
        userInfo: UserInfo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}
