package ru.gmasalskikh.imageviewer3

import android.content.res.ColorStateList
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.gmasalskikh.imageviewer3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.navHostFragment)
        binding.navMenu.setupWithNavController(navController)
        binding.navMenu.itemIconTintList = when(binding.navMenu.checkedItem?.itemId){
            R.id.gallery -> ColorStateList.valueOf(rgb(255,0,0))
            R.id.settings -> ColorStateList.valueOf(rgb(0,255,0))
            else -> ColorStateList.valueOf(rgb(0,0,255))
        }
    }
}