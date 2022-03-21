package com.bangkit.submission2github.data.di

import android.content.Context
import com.bangkit.submission2github.data.FavoriteRepository
import com.bangkit.submission2github.data.local.room.FavoriteDatabase
import com.bangkit.submission2github.data.remote.api.ApiConfig
import com.bangkit.submission2github.utils.AppExecutors


object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}