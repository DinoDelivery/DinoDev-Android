package com.dinodelivery.app.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import com.dinodelivery.app.DinoDeliveryApp.Companion.context
import java.io.File


class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    fun getCacheImagePath(fileName: String): Uri {
        val path = File(context.externalCacheDir, "camera")
        if (!path.exists()) {
            path.mkdirs()
        }
        val image = File(path, fileName)
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            image
        )
    }

    fun queryName(resolver: ContentResolver, uri: Uri): String {
        val returnCursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

}