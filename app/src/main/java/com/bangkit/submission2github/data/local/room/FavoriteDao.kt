package com.bangkit.submission2github.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.submission2github.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favo ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT count(*) FROM favo WHERE favo.id = :id")
     fun checkUser(id: Int): Int

    @Query("DELETE FROM favo WHERE favo.id = :id")
    fun deleteFavorite(id: Int): Int
}