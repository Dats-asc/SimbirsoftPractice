package com.example.simbirsoftpracticeapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvents
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.presentation.news.adapters.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsView {

    private lateinit var binding: FragmentNewsBinding

    private var filterFragment: NewsFilterFragment? = null

    private var adapter: NewsAdapter? = null

    private var events: CharityEvents? = null

    private var changedFilters: List<FilterCategory>? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsPresenter

    @ProvidePresenter
    fun providePresenter(): NewsPresenter = presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsBinding.inflate(inflater, container, false).let {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.getSerializable(EVENTS)?.let {
            this.events = (it as CharityEvents)
            initAdapter()
        } ?: kotlin.run {
            if (adapter == null) {
                presenter.getEvents()
            } else {
                binding.rvEvents.adapter = adapter
            }
        }
        init()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EVENTS, events)
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

    override fun setEvents(events: CharityEvents) {
        this.events = events
        initAdapter()
    }

    override fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showError(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        const val EVENTS = "EVENTS"
    }
}