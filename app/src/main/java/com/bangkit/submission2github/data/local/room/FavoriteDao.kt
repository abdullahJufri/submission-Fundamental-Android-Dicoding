package com.bangkit.submission2github.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.submission2github.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(user: FavoriteEntity)

    @Query("DELETE FROM favo WHERE id = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM favo ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favo WHERE id = :id")
    fun getUserById(id: Int): LiveData<FavoriteEntity>
}