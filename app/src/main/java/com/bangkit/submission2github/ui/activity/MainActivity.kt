package com.bangkit.submission2github.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submission2github.R
import com.bangkit.submission2github.ui.adapter.SearchAdapter
import com.bangkit.submission2github.databinding.ActivityMainBinding
import com.bangkit.submission2github.data.remote.model.UserItem
import com.bangkit.submission2github.ui.viewmodels.MainViewModel
import com.bangkit.submission2github.utils.Helper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchAdapter
    private val viewModel by viewModels<MainViewModel>()
    private var listUserItem = ArrayList<UserItem>()
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getListUsers.observe(this) { listGithubUser ->
            setUserData(listGithubUser)
        }

        viewModel.isLoading.observe(this) {
            helper.showLoading(it, binding.progressBar)
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUsers.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                query.let {
                    binding.rvUsers.visibility = View.VISIBLE
                    viewModel.findUsers(it)
                    setUserData(listUserItem)
                }
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.findUsers(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.theme_setting -> {
//                val intent = Intent(this@MainActivity, ThemeSettingsActivity::class.java)
//                startActivity(intent)
//                return true
//            }
            R.id.favorites -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun setUserData(listGithubUser: List<UserItem>) {
        val listUser = ArrayList<UserItem>()
        for (user in listGithubUser) {
            listUser.clear()
            listUser.addAll(listGithubUser)
        }
        adapter = SearchAdapter(listUser)
        binding.rvUsers.adapter = adapter


        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {

            override fun onItemClicked(data: UserItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data)
                    startActivity(it)
                }
            }
        })
    }
}