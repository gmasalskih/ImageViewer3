package ru.gmasalskikh.imageviewer3.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.imageviewer3.gallery.GalleryViewModel

val viewModelModules = module {
    viewModel { GalleryViewModel(androidContext()) }
}