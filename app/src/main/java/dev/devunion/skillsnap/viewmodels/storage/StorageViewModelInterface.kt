/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.viewmodels.storage

import android.graphics.Bitmap

interface StorageViewModelInterface {
    fun uploadImage(image: Bitmap, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit)
    fun deleteImage(imageUrl: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}
