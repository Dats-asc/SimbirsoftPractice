package com.example.simbirsoftpracticeapp.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private val categories by lazy {
        listOf(
            Category(0, resources.getString(R.string.kids), R.raw.ic_kids),
            Category(1, resources.getString(R.string.adults), R.raw.ic_adults),
            Category(2, resources.getString(R.string.elders), R.raw.ic_elder),
            Category(3, resources.getString(R.string.animals), R.raw.ic_animals),
            Category(4, resources.getString(R.string.events), R.raw.ic_events)
        )
    }

    private var categoryAdapter: CategoryAdapter? = null

    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHelpBinding.inflate(inflater, container, false).let {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        categoryAdapter = CategoryAdapter(categories) {
            //TODO: do on item click
        }
        binding.rvCategories.adapter = categoryAdapter
    }
}