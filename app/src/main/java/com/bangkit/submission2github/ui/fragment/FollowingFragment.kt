package com.bangkit.submission2github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submission2github.data.remote.model.UserItem
import com.bangkit.submission2github.databinding.FragmentFollowingBinding
import com.bangkit.submission2github.ui.activity.DetailUserActivity
import com.bangkit.submission2github.ui.adapter.FollowAdapter
import com.bangkit.submission2github.ui.viewmodels.FollowingViewModel
import com.bangkit.submission2github.utils.Helper


class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowingViewModel>()
    private lateinit var adapter: FollowAdapter
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            helper.showLoading(it, binding.progressBar)
        }
        viewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setDataToFragment(listFollowing)
            helper.showLoading(false, binding.progressBar)
        }

        viewModel.getFollower(arguments?.getString(DetailUserActivity.EXTRA_USERNAME).toString())
    }


    private fun setDataToFragment(listFollower: List<UserItem>) {
        val listUser = ArrayList<UserItem>()
        with(binding) {
            for (user in listFollower) {
                listUser.clear()
                listUser.addAll(listFollower)
            }
            rvFollowing.layoutManager = LinearLayoutManager(context)
            adapter = FollowAdapter(listFollower)

            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}