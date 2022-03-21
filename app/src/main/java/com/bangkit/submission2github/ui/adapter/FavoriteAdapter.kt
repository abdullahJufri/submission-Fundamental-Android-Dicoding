package com.bangkit.submission2github.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.databinding.ItemRowUserBinding
import com.bangkit.submission2github.ui.activity.DetailUserActivity
import com.bangkit.submission2github.utils.FavoriteDiff
import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorites = ArrayList<FavoriteEntity>()

    fun setFavorites(favorites: List<FavoriteEntity>) {
        val diffCallback = FavoriteDiff(this.listFavorites, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: FavoriteEntity) {
            with(binding) {
                tvItemName.text = favorites.login
                tvItemHtmlurl.text = favorites.htmlUrl
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favorites.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favorites.avatarUrl)
                .circleCrop()
                .into(binding.imgItemAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemRowUserBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = listFavorites[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int = listFavorites.size
}