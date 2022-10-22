package com.example.simbirsoftpracticeapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsFilterBinding
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.presentation.news.adapters.FilterCategoryAdapter
import com.google.android.material.snackbar.Snackbar
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NewsFilterFragment : BaseFragment(), NewsFilterView, Filterable {

    private lateinit var binding: FragmentNewsFilterBinding

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsFilterPresenter

    @ProvidePresenter
    fun providePresenter(): NewsFilterPresenter = presenter

    private var adapter: FilterCategoryAdapter? = null

    private var onFilterChanged: ((List<FilterCategory>) -> Unit)? = null

    private var filterCategories: FilterCategories? = null

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
        if (savedInstanceState == null) {
            presenter.getCategories()
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
                            presenter.updateCategories(categories)
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

    override fun onFiltersChanged(onFiltersChanged: (List<FilterCategory>) -> Unit) {
        this.onFilterChanged = onFiltersChanged
    }

    override fun setCategories(categories: FilterCategories) {
        filterCategories = categories
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
}