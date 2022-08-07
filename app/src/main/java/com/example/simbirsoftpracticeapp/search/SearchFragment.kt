package com.example.simbirsoftpracticeapp.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.appcompat.queryTextChanges


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val fragments = listOf(EventsTabFragment(), ByNkoFragment())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).let {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.root.also {
            (fragments.first() as Searchable).searchViewListener =
                binding.searchView.queryTextChanges()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
    }

    private fun initTabLayout() {
        with(binding) {
            val vpAdapter = SearchTabsAdapter(requireActivity(), fragments)
            viewpager.adapter = vpAdapter
            TabLayoutMediator(tablayout, viewpager) { tab, position ->
                when (position) {
                    0 -> tab.text = resources.getString(R.string.events)
                    1 -> tab.text = resources.getString(R.string.by_nko)
                }
            }.attach()
        }
    }


    private class SearchTabsAdapter(
        fa: FragmentActivity,
        private val tabFragments: List<Fragment>
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return tabFragments[position]
        }
    }
}