/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.viewmodels.storage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DummyStorageViewModel : ViewModel(), StorageViewModelInterface {
    override fun uploadImage(
        image: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onSuccess("https://dummyimage.com/600x400/000/fff.jpg")
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun deleteImage(
        imageUrl: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
