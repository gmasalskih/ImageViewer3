package ru.gmasalskikh.imageviewer3.gallery

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskikh.imageviewer3.data.GalleryItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class GalleryViewModel(private val context: Context) : ViewModel() {
    private val _listImgs = MutableLiveData<List<GalleryItem>>(listOf())
    val listImgs: LiveData<List<GalleryItem>>
        get() = _listImgs

    private fun addImg(pach: Uri) {
        val oldList = _listImgs.value ?: return
        _listImgs.value = oldList + listOf(
            GalleryItem(
                imgPath = pach
            )
        )
    }

    fun delImg(patch: Uri) {
        File("file://$patch").delete()
        val oldList = _listImgs.value ?: return
        _listImgs.value = oldList.filter {
            it.imgPath != patch
        }
    }

    fun saveImg(bitmap: Bitmap) {
        Log.d("---", "saveImg")
        val wrapper = ContextWrapper(context)
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
        addImg(Uri.parse(file.absolutePath))
//        return Uri.parse(file.absolutePath)
    }

}