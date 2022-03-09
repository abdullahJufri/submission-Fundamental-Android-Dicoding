package com.bangkit.submission2github.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.submission2github.model.UserItem
import com.bangkit.submission2github.ui.fragment.FollowerFragment
import com.bangkit.submission2github.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val bundle: Bundle) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = bundle
        return fragment as Fragment
    }
}