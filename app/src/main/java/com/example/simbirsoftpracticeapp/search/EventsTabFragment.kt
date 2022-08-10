package com.example.simbirsoftpracticeapp.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentEventsTabBinding
import com.example.simbirsoftpracticeapp.news.data.CharityEvent
import com.example.simbirsoftpracticeapp.news.data.CharityEvents
import com.example.simbirsoftpracticeapp.search.adapters.EventsAdapter
import com.jakewharton.rxbinding4.InitialValueObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class EventsTabFragment : Fragment(), Searchable {

    private lateinit var binding: FragmentEventsTabBinding

    private var adapter: EventsAdapter? = null

    private var events: List<CharityEvent> = listOf()

    override var searchViewListener: InitialValueObservable<CharSequence>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEventsTabBinding.inflate(inflater, container, false).let {
        binding = FragmentEventsTabBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.getSerializable(EVENTS)?.let {
            events = (it as CharityEvents).events
            initAdapter()
        }
        binding.placeholder.visibility = View.GONE
        initAdapter()
        subscribeToSearchView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EVENTS, CharityEvents(events))
    }

    private fun initAdapter() {
        binding.placeholder.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
        adapter = EventsAdapter(events) {

        }
        binding.rvEvents.adapter = adapter
        binding.rvEvents.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            ).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.item_decorator)?.let {
                    setDrawable(it)
                }
            }
        )
    }

    private fun subscribeToSearchView() {
        searchViewListener?.let {
            it.debounce(SEARCH_DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { request ->
                    onRequest(request.toString())
                }
        }
    }

    private fun onRequest(request: String) {
        if (request.isEmpty()) {
            binding.placeholder.visibility = View.VISIBLE
            binding.rvEvents.visibility = View.GONE
        } else {
            binding.rvEvents.visibility = View.VISIBLE
            binding.placeholder.visibility = View.GONE
            Utils.getEvents(requireContext())
                .map {
                    it.events.filter { event ->
                        event.title.contains(request, true)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { events ->
                    Log.e("Current thread is ", Thread.currentThread().name)
                    this.events = events
                    adapter?.updateEvents(events)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchViewListener = null
    }

    companion object {
        private const val EVENTS = "EVENTS"
        private const val SEARCH_DELAY_MILLISECONDS = 500L
    }
}