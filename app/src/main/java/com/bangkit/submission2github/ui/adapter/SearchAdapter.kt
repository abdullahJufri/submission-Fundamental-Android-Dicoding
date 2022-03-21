package com.bangkit.submission2github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submission2github.data.remote.model.UserItem
import com.bangkit.submission2github.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class SearchAdapter(private val listUser: List<UserItem>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        with(holder.binding) {
            tvItemName.text = user.login
            tvItemHtmlurl.text = user.htmlUrl
            Glide.with(root.context)
                .load(user.avatarUrl)
                .transform(CircleCrop())
                .into(imgItemAvatar)
            root.setOnClickListener { onItemClickCallback?.onItemClicked(listUser[position]) }
        }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)

    }
}
