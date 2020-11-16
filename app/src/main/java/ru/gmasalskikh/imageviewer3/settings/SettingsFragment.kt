package ru.gmasalskikh.imageviewer3.settings

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import ru.gmasalskikh.imageviewer3.BaseFragment
import ru.gmasalskikh.imageviewer3.R
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
        val nv = requireActivity().findViewById<NavigationView>(R.id.nav_menu)
        if (nv.checkedItem?.itemId == R.id.settings) nv.itemIconTintList = ColorStateList.valueOf(
            Color.rgb(0, 255, 0)
        )
        Log.d("---", requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
        return binding.root
    }
}
