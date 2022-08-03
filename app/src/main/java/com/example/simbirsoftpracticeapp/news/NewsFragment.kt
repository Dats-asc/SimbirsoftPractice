package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.simbirsoftpracticeapp.news.data.CharityEvents
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import com.example.simbirsoftpracticeapp.news.data.FilterCategory
import java.util.concurrent.Executors

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    private var filterFragment: NewsFilterFragment? = null

    private var adapter: NewsAdapter? = null

    private var events: CharityEvents? = null

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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EVENTS, events)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getSerializable(EVENTS)?.let {
            events = it as CharityEvents
            initAdapter()
        } ?: kotlin.run {
            getEvents()
        }
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = NewsAdapter(events?.events ?: listOf()) { eventId ->
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

    private fun getEvents() {
        binding.progressBar.visibility = View.VISIBLE
        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(5_000)
            events = Utils.getEvents(requireContext())
            Handler(Looper.getMainLooper()).post {
                initAdapter()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun updateEvents() {
        val filteredEvents = events?.events?.filter { event ->
            val filter = changedFilters?.find { filter -> filter.id == event.categoryId }
            filter != null
        }
        adapter?.updateEvents(filteredEvents ?: listOf())
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

    companion object {
        const val EVENTS = "EVENTS"
    }
}