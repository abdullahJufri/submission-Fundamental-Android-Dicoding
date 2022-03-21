package com.bangkit.submission2github.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.data.local.room.FavoriteDao
import com.bangkit.submission2github.data.local.room.FavoriteDatabase
import com.bangkit.submission2github.data.remote.api.ApiService
import com.bangkit.submission2github.utils.AppExecutors
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository private constructor(
    private val apiService: ApiService,

    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
)  {
//    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    fun getFavoriteUser(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavorited()
    }
    suspend fun setNewsBookmark(user: FavoriteEntity, favoriteState: Boolean) {
        //hapus penggunaan appExecutor
        user.isFavorited = favoriteState
        favoriteDao.updateUser(user)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favoriteDao, appExecutors)
            }.also { instance = it }
    }
}