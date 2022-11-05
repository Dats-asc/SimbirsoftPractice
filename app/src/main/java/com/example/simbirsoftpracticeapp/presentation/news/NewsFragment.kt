package com.example.simbirsoftpracticeapp.presentation.news

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.common.Utils.customGetParcelable
import com.example.simbirsoftpracticeapp.common.Utils.customGetSerializable
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

    private var adapter: NewsAdapter? = null

    private var events: CharityEvents? = null

    private var checkedFilters: List<FilterCategory>? = null

    private var listSate: Parcelable? = null

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
        savedInstanceState?.customGetSerializable<CharityEvents>(EVENTS)?.let {
            events = it
            initAdapter()
        } ?: kotlin.run {
            presenter.getEvents()
        }
        listSate = savedInstanceState?.customGetParcelable(LIST_STATE)
        init()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EVENTS, events)
        outState.putParcelable(LIST_STATE, listSate)
    }

    private fun initAdapter() {
        if (adapter == null) {
            events?.let {
                adapter = NewsAdapter(it.events) { eventId ->
                    findNavController().navigate(
                        R.id.action_newsFragment_to_newsDetailFragment,
                        bundleOf(Constants.EVENT_ID to eventId)
                    )
                }
                binding.rvEvents.adapter = adapter
            }
        } else {
            binding.rvEvents.adapter = adapter
        }
        listSate?.let { binding.rvEvents.layoutManager?.onRestoreInstanceState(listSate) }
    }

    private fun init() {
        with(binding) {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_filter -> {
                        setNewsFilterFragmentResultListener()
                        findNavController().navigate(R.id.newsFilterFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun updateEvents() {
        checkedFilters?.let { filters ->
            val filteredCategoriesIds = filters.map { filterCategory -> filterCategory.id }
            val filteredEvents = events?.events?.filter { event ->
                event.id in filteredCategoriesIds
            }
            adapter?.updateEvents(filteredEvents ?: listOf())
        }
    }

    private fun setNewsFilterFragmentResultListener() {
        setFragmentResultListener(RESULT_KEY) { _, bundle ->
            checkedFilters = bundle.customGetSerializable(NewsFilterFragment.CHECKED_FILTERS)
            updateEvents()
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
        const val RESULT_KEY = "RESULT_KEY"
        const val LIST_STATE = "LIST_STATE"
    }
}