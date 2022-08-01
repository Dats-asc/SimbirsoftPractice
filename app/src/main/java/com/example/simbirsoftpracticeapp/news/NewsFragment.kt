package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsBinding
import com.example.simbirsoftpracticeapp.news.adapters.NewsAdapter
import com.example.simbirsoftpracticeapp.news.data.FilterCategory

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    private var filterFragment: NewsFilterFragment? = null

    private var adapter: NewsAdapter? = null

    private val events by lazy {
        Utils.getEvents(requireContext().applicationContext)
    }

    private var changedFilters: List<FilterCategory>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsBinding.inflate(inflater, container, false).let {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initAdapter()
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = NewsAdapter(events.events) { eventId ->
                pushNewsDetail(eventId)
            }
            binding.rvEvents.adapter = adapter
        } else {
            binding.rvEvents.adapter = adapter
        }
    }

    private fun init() {
        with(binding) {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_filter -> {
                        pushFilterFragment()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun pushFilterFragment() {
        filterFragment = NewsFilterFragment().apply {
            (this as Filterable).onFiltersChanged { filters ->
                changedFilters = filters
                updateEvents()
            }
        }
        requireActivity().supportFragmentManager.beginTransaction().run {
            addToBackStack(null)
            replace(R.id.fragment_container_view, filterFragment!!)
            commit()
        }
    }

    private fun updateEvents() {
        val filteredEvents = events.events.filter { event ->
            val filter = changedFilters?.find { filter -> filter.id == event.categoryId }
            filter != null
        }
        adapter?.updateEvents(filteredEvents)
    }

    private fun pushNewsDetail(eventId: Int) {
        requireActivity().supportFragmentManager.beginTransaction().run {
            addToBackStack(this::class.java.name)
            replace(
                R.id.fragment_container_view,
                NewsDetailFragment().apply {
                    arguments = bundleOf(Constants.EVENT_ID to eventId)
                })
            commit()
        }
    }
}