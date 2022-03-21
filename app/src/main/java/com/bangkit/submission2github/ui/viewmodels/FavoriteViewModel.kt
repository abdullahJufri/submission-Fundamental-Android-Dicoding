package com.bangkit.submission2github.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.submission2github.data.FavoriteRepository
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    fun saveNews(news: FavoriteEntity) {
        viewModelScope.launch {
            favoriteRepository.setNewsBookmark(news, true)
        }
    }

    fun deleteNews(news: FavoriteEntity) {
        viewModelScope.launch {
            favoriteRepository.setNewsBookmark(news, false)
        }
    }
}