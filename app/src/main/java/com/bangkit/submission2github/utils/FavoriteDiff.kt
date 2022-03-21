package com.bangkit.submission2github.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.submission2github.data.local.entity.FavoriteEntity

class FavoriteDiff(
    private val mOldFavList: List<FavoriteEntity>,
    private val mNewFavList: List<FavoriteEntity>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavList = mOldFavList[oldItemPosition]
        val newFavList = mNewFavList[newItemPosition]
        return oldFavList.login == newFavList.login && oldFavList.htmlUrl == newFavList.htmlUrl && oldFavList.avatarUrl == newFavList.avatarUrl
    }


}