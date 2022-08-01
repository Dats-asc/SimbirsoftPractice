package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding

    private var filterFragment: NewsFilterFragment? = null

    private var changedFilters: List<FilterCategory>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsBinding.inflate(inflater, container, false).let {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti()
    }

    private fun inti() {
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
        filterFragment = NewsFilterFragment()
        (filterFragment as Filterable).onFiltersChanged { filters ->
            changedFilters = filters
        }
        requireActivity().supportFragmentManager.beginTransaction().run {
            addToBackStack(null)
            replace(R.id.fragment_container_view, filterFragment!!)
            commit()
        }
    }
}