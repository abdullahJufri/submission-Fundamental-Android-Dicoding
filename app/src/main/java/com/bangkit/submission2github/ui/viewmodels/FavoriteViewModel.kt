package com.bangkit.submission2github.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.getAllFavorites()
}