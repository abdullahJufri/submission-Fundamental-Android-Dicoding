package com.bangkit.submission2github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submission2github.adapter.FollowAdapter
import com.bangkit.submission2github.databinding.FragmentFollowerBinding
import com.bangkit.submission2github.model.UserItem
import com.bangkit.submission2github.ui.activity.DetailUserActivity
import com.bangkit.submission2github.ui.viewmodels.FollowerViewModel
import com.bangkit.submission2github.utils.Event

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowerViewModel>()
    private lateinit var adapter: FollowAdapter
    private val event = Event()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            event.showLoading(it, binding.progressBar)
        }
        viewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
            event.showLoading(false, binding.progressBar)
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
            rvFollower.layoutManager = LinearLayoutManager(context)
            adapter = FollowAdapter(listFollower)

            rvFollower.setHasFixedSize(true)
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.adapter = adapter


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
