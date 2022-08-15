package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.data.CharityRepository
import com.example.simbirsoftpracticeapp.data.database.AppDatabase
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsBinding
import com.example.simbirsoftpracticeapp.news.adapters.NewsAdapter
import com.example.simbirsoftpracticeapp.news.data.CharityEvents
import com.example.simbirsoftpracticeapp.news.data.FilterCategory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    private var filterFragment: NewsFilterFragment? = null

    private var adapter: NewsAdapter? = null

    private var events: CharityEvents? = null

    private var changedFilters: List<FilterCategory>? = null

    private val charityApi = CharityRepository().charityApi()

    private val db by lazy { AppDatabase(requireContext()) }

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
                getEventsFromDb()
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

    private fun getEventsFromDb() {
        db.charityEventsDao().getAll()
            .map { eventsEntity ->
                eventsEntity.map { eventEntity ->
                    Utils.mapCharityEventEntity(eventEntity)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { binding.progressBar.visibility = View.VISIBLE }
            .doAfterSuccess { binding.progressBar.visibility = View.GONE }
            .subscribe({ events ->
                if (events.isNullOrEmpty()) {
                    getEvents()
                } else {
                    this.events = CharityEvents(events)
                    initAdapter()
                }
            }, { e ->
                Log.e(this.tag, e.message.orEmpty())
            })
    }

    private fun getEvents() {
        charityApi.getEvents()
            .delay(1000, TimeUnit.MILLISECONDS)
            .map { events -> CharityEvents(events) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { binding.progressBar.visibility = View.VISIBLE }
            .doAfterSuccess { binding.progressBar.visibility = View.GONE }
            .subscribe({ events ->
                this.events = events
                initAdapter()
                db.charityEventsDao()
                    .insertAll(events.events.map { event -> Utils.mapCharityEvent(event) })
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.e("", "Events inserted into database") },
                        { e -> Log.e(this.tag, e.message.orEmpty()) })
            }, { e ->
                Log.e(this.tag, e.message.orEmpty())
            })
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