/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.viewmodels.storage

import android.graphics.Bitmap

interface StorageViewModelInterface {
    fun uploadImage(image: Bitmap, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit)
    fun deleteImage(imageUrl: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}
