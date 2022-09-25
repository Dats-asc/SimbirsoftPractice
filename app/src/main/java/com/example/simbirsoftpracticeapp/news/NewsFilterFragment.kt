package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsFilterBinding
import com.example.simbirsoftpracticeapp.news.adapters.FilterCategoryAdapter
import com.example.simbirsoftpracticeapp.news.data.FilterCategory

class NewsFilterFragment : Fragment(), Filterable {

    private lateinit var binding: FragmentNewsFilterBinding

    private var adapter: FilterCategoryAdapter? = null

    private var onFilterChanged: ((List<FilterCategory>) -> Unit)? = null

    private val filterCategories by lazy {
        Utils.getCategories(requireContext().applicationContext)
    }

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

    private fun init() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_apply -> {
                        onFilterChanged?.invoke(filterCategories.categories.filter { filterCategory -> filterCategory.isChecked })
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }

        initAdapter()
    }

    private fun initAdapter() {
        adapter = FilterCategoryAdapter(filterCategories.categories) { categoryId, isChecked ->
            filterCategories.categories.find { category -> category.id == categoryId }?.let {
                it.isChecked = isChecked
            }
        }
        binding.rvCategories.adapter = adapter
    }

    override fun onFiltersChanged(onFiltersChanged: (List<FilterCategory>) -> Unit) {
        this.onFilterChanged = onFiltersChanged
    }
}