package ru.gmasalskikh.imageviewer3.utils

import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import ru.gmasalskikh.imageviewer3.data.GalleryItem
import ru.gmasalskikh.imageviewer3.gallery.GalleryAdapter

@BindingAdapter(value = ["galleryItem", "longClickListener"], requireAll = false)
fun CardView.setMyLongClickListener(galleryItem: GalleryItem, longClickListener: GalleryAdapter.GalleryItemLongClickListener) {
    this.setOnLongClickListener {
        longClickListener.longClickListener(galleryItem)
        return@setOnLongClickListener true
    }
}