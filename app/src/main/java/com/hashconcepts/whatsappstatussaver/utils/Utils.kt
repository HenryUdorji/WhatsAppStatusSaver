package com.hashconcepts.whatsappstatussaver.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.util.*

class Utils {

    fun isImageFile(context: Context, path: String): Boolean {
        val uri = Uri.parse(path)
        val mimeType = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val type = context.contentResolver
            type.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase(Locale.ROOT))
        }
        return mimeType != null && mimeType.startsWith("image")
    }
}