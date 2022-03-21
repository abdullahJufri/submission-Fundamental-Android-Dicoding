package com.bangkit.submission2github.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favo")
@Parcelize
class FavoriteEntity (
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int,

    @field:ColumnInfo(name = "login")
    var login: String?,

    @field:ColumnInfo(name = "html_url")
    var htmlUrl: String? = null,

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "favorited")
    var isFavorited: Boolean


) :Parcelable