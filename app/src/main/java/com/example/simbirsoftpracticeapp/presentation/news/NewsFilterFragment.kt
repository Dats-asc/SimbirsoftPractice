package com.example.simbirsoftpracticeapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.common.Utils.customGetSerializable
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsFilterBinding
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.presentation.news.adapters.FilterCategoryAdapter
import com.google.android.material.snackbar.Snackbar
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NewsFilterFragment : BaseFragment(), NewsFilterView {

    private lateinit var binding: FragmentNewsFilterBinding

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsFilterPresenter

    @ProvidePresenter
    fun providePresenter(): NewsFilterPresenter = presenter

    private var adapter: FilterCategoryAdapter? = null

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
        savedInstanceState?.customGetSerializable<FilterCategories>(FILTER_CATEGORIES)?.let {
            filterCategories = it
            initAdapter()
        } ?: kotlin.run {
            presenter.getCategories()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(FILTER_CATEGORIES, filterCategories)
    }

    private fun init() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_apply -> {
                        filterCategories?.categories?.let { categories ->
                            presenter.updateCategories(categories)
                            val checkedFilters =
                                categories.filter { category -> category.isChecked }
                            setFragmentResult(
                                NewsFragment.RESULT_KEY,
                                bundleOf(CHECKED_FILTERS to checkedFilters)
                            )
                        }
                        findNavController().navigateUp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initAdapter() {
        filterCategories?.let {
            adapter = FilterCategoryAdapter(
                it.categories
            ) { categoryId, isChecked ->
                filterCategories?.categories?.find { category -> category.id == categoryId }?.let {
                    it.isChecked = isChecked
                }
            }
            binding.rvCategories.adapter = adapter
        }
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

    companion object {
        const val CHECKED_FILTERS = "CHECKED_FILTERS"
        const val FILTER_CATEGORIES = "FILTER_CATEGORIES"
    }
}