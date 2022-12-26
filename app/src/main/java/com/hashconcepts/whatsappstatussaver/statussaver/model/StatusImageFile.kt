package com.hashconcepts.whatsappstatussaver.statussaver.model

import android.net.Uri

data class StatusImageFile(
    val id: Long,
    val name: String,
    val width: Int,
    val height: Int,
    val contentUri: Uri
)
