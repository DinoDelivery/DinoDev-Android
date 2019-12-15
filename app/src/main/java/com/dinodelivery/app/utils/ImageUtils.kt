package com.dinodelivery.app.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Base64
import com.dinodelivery.app.DinoDeliveryApp
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException

object ImageUtils {

    fun encodeToBase64(uri: Uri): String? {
        val image = uriToBitmap(uri)
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun decodeBase64(input: String?): Bitmap? {
        val decodedByte: ByteArray = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            val parcelFileDescriptor: ParcelFileDescriptor? =
                DinoDeliveryApp.context.contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

}