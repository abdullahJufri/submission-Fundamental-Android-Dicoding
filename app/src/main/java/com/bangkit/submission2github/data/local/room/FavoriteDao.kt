package com.bangkit.submission2github.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.submission2github.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favo ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favo where favorited = 1")
    fun getFavorited(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

//    @Query("SELECT count(*) FROM favo WHERE favo.id = :id")
//     fun checkUser(id: Int): Int

    @Update
    suspend fun updateUser(user: FavoriteEntity)

    @Query("DELETE FROM favo WHERE favo.id = :id")
    suspend fun removeFavorite(id: Int): Int

    @Query("SELECT EXISTS(SELECT * FROM favo WHERE id = :id AND favorited = 1)")
    suspend fun isFavorited(id: Int): Boolean
}