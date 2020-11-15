package ru.gmasalskikh.imageviewer3.image_viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import coil.load
import ru.gmasalskikh.imageviewer3.BaseFragment
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