package com.example.simbirsoftpracticeapp.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsFilterBinding
import com.example.simbirsoftpracticeapp.news.adapters.FilterCategoryAdapter
import com.example.simbirsoftpracticeapp.news.data.FilterCategories
import com.example.simbirsoftpracticeapp.news.data.FilterCategory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

class NewsFilterFragment : Fragment(), Filterable {

    private lateinit var binding: FragmentNewsFilterBinding

    private var adapter: FilterCategoryAdapter? = null

    private var onFilterChanged: ((List<FilterCategory>) -> Unit)? = null

    private var filterCategories: FilterCategories? = null

    private var broadcastReceiver: CategoriesBroadcastReceiver? = null

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

    private fun getFilterCategoriesWithAsyncTask() {
        val getFilterCategoriesAsync = object : AsyncTask<Context, Unit, FilterCategories>() {
            override fun doInBackground(vararg p0: Context?): FilterCategories? {
                var categories: FilterCategories? = null
                Utils.getCategoriesRxJava(p0.first()!!)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe {
                        Log.e("Current thread is ", Thread.currentThread().name)
                        categories = it
                    }
                return categories
            }

            override fun onPostExecute(result: FilterCategories?) {
                super.onPostExecute(result)
                Handler(Looper.getMainLooper()).post {
                    filterCategories = result
                    initAdapter()
                }
            }
        }
        getFilterCategoriesAsync.execute(requireActivity())

    }

    private fun getFilterCategoriesWithExecutor() {
        binding.progressBar.visibility = View.VISIBLE
        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(5_000)
            Utils.getCategoriesRxJava(requireContext())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e("Current thread is ", Thread.currentThread().name)
                    filterCategories = it
                    initAdapter()
                    binding.progressBar.visibility = View.GONE
                }
        }
    }

    private fun getFilterCategoriesWithIntentService() {
        broadcastReceiver = CategoriesBroadcastReceiver()
        requireActivity().startService(
            Intent(
                requireContext(),
                CategoriesIntentService::class.java
            )
        )

        requireContext().registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.ACTION_SEND_CATEGORIES).apply { addCategory(Intent.CATEGORY_DEFAULT) })

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