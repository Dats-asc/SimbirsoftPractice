package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsFilterBinding

class NewsFilterFragment : Fragment(), Filterable {

    private lateinit var binding: FragmentNewsFilterBinding

    private var adapter: FilterCategoryAdapter? = null

    private var onFilterChanged: ((List<FilterCategory>) -> Unit)? = null

    private val filterCategories by lazy {
        listOf(
            FilterCategory(0, resources.getString(R.string.kids), true),
            FilterCategory(1, resources.getString(R.string.adults), true),
            FilterCategory(2, resources.getString(R.string.elders), true),
            FilterCategory(3, resources.getString(R.string.animals), true),
            FilterCategory(4, resources.getString(R.string.events), true),
        )
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
                        val a = onFilterChanged?.invoke(filterCategories)
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
        adapter = FilterCategoryAdapter(filterCategories) { categoryId, isChecked ->
            filterCategories.find { category -> category.id == categoryId }?.let {
                it.isChecked = isChecked
            }
        }
        binding.rvCategories.adapter = adapter
    }

    override fun onFiltersChanged(onFiltersChanged: (List<FilterCategory>) -> Unit) {
        this.onFilterChanged = onFiltersChanged
    }
}