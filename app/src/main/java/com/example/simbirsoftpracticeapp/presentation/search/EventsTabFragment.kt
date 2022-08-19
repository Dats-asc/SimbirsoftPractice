package com.example.simbirsoftpracticeapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.databinding.FragmentEventsTabBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvents
import com.example.simbirsoftpracticeapp.presentation.search.adapters.EventsAdapter
import com.jakewharton.rxbinding4.InitialValueObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EventsTabFragment : BaseFragment(), EventsTabView, Searchable {

    private lateinit var binding: FragmentEventsTabBinding

    @Inject
    @InjectPresenter
    lateinit var presenter: EventsTabPresenter

    @ProvidePresenter
    fun providePresenter(): EventsTabPresenter = presenter

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
        if (request.isNotEmpty()) {
            presenter.search(request)
        } else {
            showPlaceholder()
        }
    }

    override fun updateList(events: List<CharityEvent>) {
        this.events = events
        adapter?.updateEvents(events)
    }

    override fun showPlaceholder() {
        binding.placeholder.visibility = View.VISIBLE
        binding.rvEvents.visibility = View.GONE
    }

    override fun hidePlaceholder() {
        binding.rvEvents.visibility = View.VISIBLE
        binding.placeholder.visibility = View.GONE
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