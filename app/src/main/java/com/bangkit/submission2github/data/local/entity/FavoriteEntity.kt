package com.bangkit.submission2github.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favo")
class FavoriteEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "html_url")
    var htmlUrl: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,


)