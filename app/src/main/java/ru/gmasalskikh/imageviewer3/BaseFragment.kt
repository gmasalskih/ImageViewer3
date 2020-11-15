package ru.gmasalskikh.imageviewer3

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment : Fragment() {
    protected lateinit var navController: NavController
    abstract val toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        val appBarConfiguration = AppBarConfiguration(
            navGraph = navController.graph,
            drawerLayout = requireActivity().drawerLayout
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}