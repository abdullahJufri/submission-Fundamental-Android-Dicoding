package com.bangkit.submission2github.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.submission2github.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(user: FavoriteEntity)

    @Query("DELETE FROM favo WHERE favo.id = :id")
    fun removeFavorite(id: Int)

    @Query("SELECT * FROM favo ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favo WHERE favo.id = :id")
    fun getUserById(id: Int): LiveData<FavoriteEntity>
}