package com.bangkit.submission2github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submission2github.data.remote.model.UserItem
import com.bangkit.submission2github.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide

class FollowAdapter(private val listFollower: List<UserItem>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollower[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(imgItemAvatar)
            tvItemName.text = follower.login
            tvItemHtmlurl.text = follower.htmlUrl
        }
    }

    override fun getItemCount(): Int = listFollower.size


}