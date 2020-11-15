package ru.gmasalskikh.imageviewer3.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gmasalskikh.imageviewer3.data.GalleryItem
import ru.gmasalskikh.imageviewer3.databinding.GalleryItemBinding
import java.io.File

class GalleryAdapter(
    private val clickListener: GalleryItemClickListener,
    private val longClickListener: GalleryItemLongClickListener
) : ListAdapter<GalleryItem, GalleryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, longClickListener)
    }

    class ViewHolder private constructor(private val binding: GalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: GalleryItem,
            clickListener: GalleryItemClickListener,
            longClickListener: GalleryItemLongClickListener
        ) {
            Log.d("---", item.imgPath.toString())
            binding.img.load(File(item.imgPath.toString()))
            binding.galleryItem = item
            binding.clickListener = clickListener
            binding.longClickListener = longClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GalleryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.isFavorite == newItem.isFavorite &&
                    oldItem.imgPath == newItem.imgPath
        }
    }

    class GalleryItemClickListener(val clickListener: (note: GalleryItem) -> Unit) {
        fun onClick(note: GalleryItem) = clickListener(note)
    }

    class GalleryItemLongClickListener(val longClickListener: (note: GalleryItem) -> Unit) {
        fun onLongClick(note: GalleryItem) = longClickListener(note)
    }
}