package com.bangkit.submission2github.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.submission2github.R
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.data.remote.model.DetailResponse
import com.bangkit.submission2github.databinding.ActivityDetailUserBinding
import com.bangkit.submission2github.ui.adapter.SectionsPagerAdapter
import com.bangkit.submission2github.ui.viewmodels.DetailUserViewModel
import com.bangkit.submission2github.ui.viewmodels.ViewModelFactory
import com.bangkit.submission2github.utils.Helper
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private val helper = Helper()

    private var detailUser = DetailResponse()
    private var buttonState: Boolean = false
    private var favoriteUser: FavoriteEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = obtainViewModel(this@DetailUserActivity)


        val actionbar = supportActionBar
        //set actionbar title
        actionbar?.title = "Detail User "
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)


        viewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar)
        }

        viewModel.listDetail.observe(this) { listGithubUser ->
            setDetailItem(listGithubUser)
            helper.showLoading(false, binding.progressBar)
        }

        viewModel.status.observe(this, { status ->
            status?.let {
                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTabLayout()


        viewModel.listDetail.observe(this, { detailList ->
            detailUser = detailList
            setDetailItem(detailUser)
            favoriteUser = FavoriteEntity(detailUser.id, detailUser.login)
            viewModel.getAllFavorites().observe(this, { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (detailUser.id == data.id) {
                            buttonState = true
                            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24)
                        }
                    }
                }
            })

            // Favorite event
            binding.fabFavorite.setOnClickListener {
                if (!buttonState) {
                    buttonState = true
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_24)
                    insertToDatabase(detailUser)
                } else {
                    buttonState = false
                    binding.fabFavorite.setImageResource(R.drawable.ic_unfavorite_24)
                    viewModel.delete(detailUser.id)
                    helper.showToast(this, "User dihapus dari Favorit.")
                }
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun setDetailItem(detailList: DetailResponse) {

        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(imgDetailAvatar)
            tvDetailName.text = detailList.name ?: "No Name"
            tvDetailUsername.text = detailList.login
            tvDetailLocation.text = detailList.location ?: "No location"
            tvDetailCompany.text = detailList.company ?: "No Company"
            tvDetailRepo.text = resources.getString(R.string.repository, detailList.publicRepos)
            tvDetailFollower.text = resources.getString(R.string.follower, detailList.followers)
            tvDetailFollowing.text =
                resources.getString(R.string.following, detailList.following)
        }
    }

    private fun insertToDatabase(detailList: DetailResponse) {
        favoriteUser.let { favoriteUser ->
            favoriteUser?.id = detailList.id
            favoriteUser?.login = detailList.login
            favoriteUser?.htmlUrl = detailList.htmlUrl
            favoriteUser?.avatarUrl = detailList.avatarUrl
            viewModel.insert(favoriteUser as FavoriteEntity)
            helper.showToast(this, "User Ditambahkan ke Favorit.")
        }
    }

    private fun setTabLayout() {
        val dataIntent = intent.extras


        if (dataIntent != null) {
            val userLogin = dataIntent.getString(EXTRA_USERNAME)
            if (userLogin != null) {
                viewModel.getUserItem(userLogin)
                val login = Bundle()
                login.putString(EXTRA_USERNAME, userLogin)
                val sectionPagerAdapter = SectionsPagerAdapter(this, login)
                val viewPager: ViewPager2 = binding.viewPager

                viewPager.adapter = sectionPagerAdapter
                val tabs: TabLayout = binding.tabs
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        supportActionBar?.elevation = 0f

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_F = "extra_f"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
}