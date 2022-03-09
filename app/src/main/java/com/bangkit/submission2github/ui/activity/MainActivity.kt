package com.bangkit.submission2github.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submission2github.R
import com.bangkit.submission2github.adapter.SearchAdapter
import com.bangkit.submission2github.databinding.ActivityMainBinding
import com.bangkit.submission2github.model.UserItem
import com.bangkit.submission2github.ui.viewmodels.MainViewModel
import com.bangkit.submission2github.utils.Event

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchAdapter
    private val viewModel by viewModels<MainViewModel>()
    private var listUserItem = ArrayList<UserItem>()
    private val event = Event()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getListUsers.observe(this, { listGithubUser ->
            setUserData(listGithubUser)
        })

        viewModel.isLoading.observe(this, {
            event.showLoading(it, binding.progressBar)
        })


//
//        viewModel.getTotalCount.observe(this, {
//            showText(it)
//        })

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUsers.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                query.let {
                    binding.rvUsers.visibility = View.VISIBLE
                    viewModel.findUsers(it)
                    setUserData(listUserItem)
                }
//                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.findUsers(query)
                return false
            }
        })
        return true
    }

    private fun setUserData(listGithubUser: List<UserItem>) {
        val listUser = ArrayList<UserItem>()
        for (user in listGithubUser) {
            listUser.clear()
            listUser.addAll(listGithubUser)
        }
        adapter = SearchAdapter(listUser)
        binding.rvUsers.adapter = adapter
//
//        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: UserItem) {
//                showSelectedUser(data)
//            }
//        })

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {

            override fun onItemClicked(data: UserItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data)
                    startActivity(it)
                }
            }
        })
    }

    private fun showSelectedUser(data: UserItem) {
        val moveWithParcelableIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
        startActivity(moveWithParcelableIntent)
    }


}