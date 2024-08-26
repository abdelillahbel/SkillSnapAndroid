/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.viewmodels.storage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class FirebaseStorageViewModel : ViewModel(), StorageViewModelInterface {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val user = Firebase.auth.currentUser

    override fun uploadImage(
        image: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/portfolio_app/${user!!.uid}/$uuid.jpg")

        val byteArray = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArray)
        val imageData = byteArray.toByteArray()

        val uploadTask = imageRef.putBytes(imageData)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                onSuccess(downloadUri.toString())
            } else {
                onFailure(task.exception!!)
            }
        }
    }

    override fun deleteImage(
        imageUrl: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val imageRef = storage.getReferenceFromUrl(imageUrl)
        imageRef.delete().addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }
}
