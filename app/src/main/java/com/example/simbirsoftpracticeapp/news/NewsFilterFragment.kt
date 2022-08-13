package com.example.simbirsoftpracticeapp.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.data.CharityRepository
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsFilterBinding
import com.example.simbirsoftpracticeapp.news.adapters.FilterCategoryAdapter
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import com.example.simbirsoftpracticeapp.news.data.FilterCategory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NewsFilterFragment : Fragment(), Filterable {

    private lateinit var binding: FragmentNewsFilterBinding

    private var adapter: FilterCategoryAdapter? = null

    private var onFilterChanged: ((List<FilterCategory>) -> Unit)? = null

    private var filterCategories: FilterCategories? = null

    private var broadcastReceiver: CategoriesBroadcastReceiver? = null

    private val charityApi = CharityRepository().charityApi()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsFilterBinding.inflate(inflater, container, false).let {
        binding = FragmentNewsFilterBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(FILTER_CATEGORIES, filterCategories)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getSerializable(FILTER_CATEGORIES)?.let {
            filterCategories = it as FilterCategories
            initAdapter()
        } ?: kotlin.run {
            getFilterCategoriesWithExecutor()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        broadcastReceiver?.let {
            requireContext().unregisterReceiver(it)
        }
    }

    private fun init() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_apply -> {
                        filterCategories?.categories?.let { categories ->
                            onFilterChanged?.invoke(categories.filter { category -> category.isChecked })
                        }
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initAdapter() {
        adapter = FilterCategoryAdapter(
            filterCategories?.categories ?: listOf()
        ) { categoryId, isChecked ->
            filterCategories?.categories?.find { category -> category.id == categoryId }?.let {
                it.isChecked = isChecked
            }
        }
        binding.rvCategories.adapter = adapter
    }

    private fun getFilterCategoriesWithExecutor() {
        charityApi.getCategories()
            .delay(1000, TimeUnit.MILLISECONDS)
            .map { categories -> FilterCategories(categories) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { binding.progressBar.visibility = View.VISIBLE }
            .doAfterSuccess { binding.progressBar.visibility = View.GONE }
            .subscribe({ categories ->
                filterCategories = categories
                initAdapter()
            }, { e ->
                Log.e(this.tag, e.message.orEmpty())
                Utils.getCategories(requireContext())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { binding.progressBar.visibility = View.GONE }
                    .subscribe({ categories ->
                        filterCategories = categories
                        initAdapter()
                    }, { e ->
                        Log.e(this.tag, e.message.orEmpty())
                    })
            })
    }

    override fun onFiltersChanged(onFiltersChanged: (List<FilterCategory>) -> Unit) {
        this.onFilterChanged = onFiltersChanged
    }

    companion object {
        const val FILTER_CATEGORIES = "FILTER_CATEGORIES"
    }

    inner class CategoriesBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            val result = p1?.extras?.getSerializable(Constants.EXTRA_CATEGORIES)
            result?.let {
                filterCategories = it as FilterCategories
                initAdapter()
            }
        }
    }

}