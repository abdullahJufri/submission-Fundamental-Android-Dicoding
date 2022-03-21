package com.bangkit.submission2github.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.data.local.room.FavoriteDao
import com.bangkit.submission2github.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllUser()

    fun insert(user: FavoriteEntity) {
        executorService.execute { mFavoriteDao.insertFavorite(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteDao.removeFavorite(id) }
    }
}