package ru.gmasalskikh.imageviewer3.gallery

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.gmasalskikh.imageviewer3.BaseFragment
import ru.gmasalskikh.imageviewer3.R
import ru.gmasalskikh.imageviewer3.data.GalleryItem
import ru.gmasalskikh.imageviewer3.databinding.FragmentGalleryBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class GalleryFragment : BaseFragment() {
    private lateinit var binding: FragmentGalleryBinding
    override lateinit var toolbar: Toolbar
    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = GalleryAdapter(
            clickListener = GalleryAdapter.GalleryItemClickListener { item ->

            },
            longClickListener = GalleryAdapter.GalleryItemLongClickListener { item ->
                createAlertDialog(item).show()
            }
        )
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.rvGallery.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvGallery.adapter = adapter
        toolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            val takePicture = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 0)


//            val pickPhoto = Intent(
//                Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            )
//            startActivityForResult(pickPhoto, 0)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createAlertDialog(galleryItem: GalleryItem) = AlertDialog.Builder(requireActivity())
        .setCancelable(true)
        .setTitle("Delete image")
        .setMessage("Are you sure?")
        .setPositiveButton("Yes") { _, _ ->
            delFile(galleryItem.imgPath)
            val newList = adapter.currentList.filter {
                it.imgPath != galleryItem.imgPath
            }
            adapter.submitList(newList)
        }.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }.create()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == RESULT_OK && data?.extras != null) {
            val selectedImage = data.extras?.get("data") as Bitmap
            val list = adapter.currentList
            val urlImg = saveImg(selectedImage)
            adapter.submitList(list + listOf(GalleryItem(imgPath = urlImg)))
        }
    }

    private fun delFile(uri: Uri) {
        File("file://$uri").delete()
    }

    private fun saveImg(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.png")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            Log.d("---", "${e.message}")
        }
        Log.d("---", Uri.parse(file.absolutePath).toString())
        return Uri.parse(file.absolutePath)
    }
}