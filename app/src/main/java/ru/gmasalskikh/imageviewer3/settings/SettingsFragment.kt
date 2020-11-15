package ru.gmasalskikh.imageviewer3.settings

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import ru.gmasalskikh.imageviewer3.BaseFragment
import ru.gmasalskikh.imageviewer3.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding
    override lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        toolbar = binding.toolbar
        Log.d("---", requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
        return binding.root
    }
}