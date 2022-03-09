package com.bangkit.submission2github.ui.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.submission2github.R
import com.bangkit.submission2github.databinding.ActivityDetailUserBinding
import com.bangkit.submission2github.model.DetailResponse
import com.bangkit.submission2github.ui.viewmodels.DetailUserViewModel
import com.bangkit.submission2github.utils.Event
import com.bumptech.glide.Glide

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel>()
    private val event = Event()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username: String = intent.getStringExtra(EXTRA_USERNAME).toString()
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            viewModel.getUserItem(username)
        } else {
            Log.d(ContentValues.TAG, "onFailure")
        }

        viewModel.listDetail.observe(this, { listGithubUser ->
            setDetailItem(listGithubUser)
        })


        viewModel.isLoading.observe(this, {
            event.showLoading(it, binding.progressBar)
        })


    }

    private fun setDetailItem(detailList: DetailResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(imgDetailAvatar)
            tvDetailName.text = detailList.name ?: "No Name."
            tvDetailUsername.text = detailList.login
            tvDetailLocation.text = detailList.location ?: "No location."
            tvDetailCompany.text = detailList.company ?: "No company."
            tvDetailRepo.text = resources.getString(R.string.repository, detailList.publicRepos)
            tvDetailFollower.text = resources.getString(R.string.follower, detailList.followers)
            tvDetailFollowing.text = resources.getString(R.string.following, detailList.following)


        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

    }
}