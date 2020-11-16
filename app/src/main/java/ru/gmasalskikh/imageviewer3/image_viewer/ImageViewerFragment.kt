package ru.gmasalskikh.imageviewer3.image_viewer

import android.content.res.ColorStateList
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import coil.load
import com.google.android.material.navigation.NavigationView
import ru.gmasalskikh.imageviewer3.BaseFragment
import ru.gmasalskikh.imageviewer3.R
import ru.gmasalskikh.imageviewer3.databinding.FragmentImageViewerBinding
import java.io.File

class ImageViewerFragment : BaseFragment() {
    override lateinit var toolbar: Toolbar
    lateinit var binding: FragmentImageViewerBinding
    lateinit var args: ImageViewerFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        args = ImageViewerFragmentArgs.fromBundle(requireArguments())
        binding.imageViewer.load(File(args.path))
        toolbar = binding.toolbar
        return binding.root
    }


}