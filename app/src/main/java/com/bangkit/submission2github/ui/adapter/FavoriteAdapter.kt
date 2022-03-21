package com.bangkit.submission2github.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submission2github.R
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.databinding.ActivityDetailUserBinding
import com.bangkit.submission2github.databinding.ItemRowUserBinding
import com.bangkit.submission2github.ui.activity.DetailUserActivity
import com.bangkit.submission2github.utils.FavoriteDiff
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteAdapter(private val onBookmarkClick: (FavoriteEntity) -> Unit) : ListAdapter<FavoriteEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ActivityDetailUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        val ivBookmark = holder.binding.ivBookmark
        if (user.isFavorited) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_favorite_24))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_unfavorite_24))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(user)
        }
    }

    class MyViewHolder(val binding: ActivityDetailUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(user: FavoriteEntity) {
            binding.tvDetailName.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
//                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgDetailAvatar)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(user.htmlUrl)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEntity> =
            object : DiffUtil.ItemCallback<FavoriteEntity>() {
                override fun areItemsTheSame(oldUser: FavoriteEntity, newUser: FavoriteEntity): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: FavoriteEntity, newUser: FavoriteEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}