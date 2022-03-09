package com.bangkit.submission2github.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.submission2github.R
import com.bangkit.submission2github.adapter.SectionsPagerAdapter
import com.bangkit.submission2github.databinding.ActivityDetailUserBinding
import com.bangkit.submission2github.model.DetailResponse
import com.bangkit.submission2github.model.UserItem
import com.bangkit.submission2github.ui.viewmodels.DetailUserViewModel
import com.bangkit.submission2github.utils.Event
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel>()
    private val event = Event()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.isLoading.observe(this, {
            event.showLoading(it, binding.progressBar)
        })

        viewModel.listDetail.observe(this, { listGithubUser ->
            setDetailItem(listGithubUser)
            event.showLoading(false, binding.progressBar)
        })




        setTabLayout()



    }

    private fun setDetailItem(detailList: DetailResponse) {

            binding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(detailList.avatarUrl)
                    .circleCrop()
                    .into(imgDetailAvatar)
                tvDetailName.text = detailList.name ?: detailList.location
                tvDetailUsername.text = detailList.login
                tvDetailLocation.text = detailList.location ?: "No location"
                tvDetailCompany.text = detailList.company ?: "No Company"
                tvDetailRepo.text = resources.getString(R.string.repository, detailList.publicRepos)
                tvDetailFollower.text = resources.getString(R.string.follower, detailList.followers)
                tvDetailFollowing.text =
                    resources.getString(R.string.following, detailList.following)



        }
    }

    private fun setTabLayout() {
        val dataIntent = intent.getParcelableExtra<UserItem>(EXTRA_USERNAME) as UserItem
        val username: String = dataIntent.login
        viewModel.getUserItem(username)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )

    }
}