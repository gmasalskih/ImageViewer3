package ru.gmasalskikh.imageviewer3.data

import android.net.Uri
import java.net.URL
import java.util.*

data class GalleryItem(
    val id: UUID = UUID.randomUUID(),
    val isFavorite: Boolean = false,
    val imgPath: Uri
)