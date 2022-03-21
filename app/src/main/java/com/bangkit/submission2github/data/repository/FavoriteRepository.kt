package com.bangkit.submission2github.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.data.local.room.FavoriteDao
import com.bangkit.submission2github.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        favoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = favoriteDao.getAllUser()

    fun insertUserFavorite(user: FavoriteEntity) {
        executorService.execute { favoriteDao.addFavorite(user) }
    }

    fun deleteUserFavorite(id: Int) {
        executorService.execute { favoriteDao.deleteFavorite(id) }
    }
}