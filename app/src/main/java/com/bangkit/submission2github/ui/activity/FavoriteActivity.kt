package com.bangkit.submission2github.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submission2github.R
import com.bangkit.submission2github.databinding.ActivityFavoriteBinding
import com.bangkit.submission2github.ui.adapter.FavoriteAdapter
import com.bangkit.submission2github.ui.viewmodels.DetailUserViewModel
import com.bangkit.submission2github.ui.viewmodels.FavoriteViewModel
import com.bangkit.submission2github.ui.viewmodels.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteAdapter

//    private val viewModel by viewModels<FavoriteViewModel>()

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = obtainViewModel(this@FavoriteActivity)
        viewModel.getAllFavorites().observe(this, { favoriteList ->
            if (favoriteList != null) {
                adapter.setFavorites(favoriteList)
            }
        })
        adapter = FavoriteAdapter()
        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorites?.setHasFixedSize(false)
        binding?.rvFavorites?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}