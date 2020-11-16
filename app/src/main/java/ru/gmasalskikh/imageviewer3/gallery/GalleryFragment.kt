package ru.gmasalskikh.imageviewer3.gallery

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color.rgb
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    private val viewModel: GalleryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = GalleryAdapter(
            clickListener = GalleryAdapter.GalleryItemClickListener { item ->
                val action =
                    GalleryFragmentDirections.actionGalleryToImageViewer(item.imgPath.toString())
                navController.navigate(action)
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
        viewModel.listImgs.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        val nv = requireActivity().findViewById<NavigationView>(R.id.nav_menu)
        if (nv.checkedItem?.itemId == R.id.gallery) nv.itemIconTintList = ColorStateList.valueOf(rgb(255,0,0))
        binding.fab.setOnClickListener {
            val takePicture = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 0)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createAlertDialog(galleryItem: GalleryItem) = AlertDialog.Builder(requireActivity())
        .setCancelable(true)
        .setTitle("Delete image")
        .setMessage("Are you sure?")
        .setPositiveButton("Yes") { _, _ ->
            viewModel.delImg(galleryItem.imgPath)
//            delFile(galleryItem.imgPath)
//            val newList = adapter.currentList.filter {
//                it.imgPath != galleryItem.imgPath
//            }
//            adapter.submitList(newList)
        }.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }.create()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == RESULT_OK && data?.extras != null) {
            Log.d("---", "onActivityResult")
            (data.extras?.get("data") as Bitmap).let { bitmap -> viewModel.saveImg(bitmap) }
        }
    }

//    private fun delFile(uri: Uri) {
//        File("file://$uri").delete()
//    }

//    private fun saveImg(bitmap: Bitmap): Uri {
//        val wrapper = ContextWrapper(requireContext())
//        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
//        file = File(file, "${UUID.randomUUID()}.png")
//        try {
//            val stream: OutputStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            stream.flush()
//            stream.close()
//        } catch (e: IOException) {
//            Log.d("---", "${e.message}")
//        }
//        Log.d("---", Uri.parse(file.absolutePath).toString())
//        return Uri.parse(file.absolutePath)
//    }
}