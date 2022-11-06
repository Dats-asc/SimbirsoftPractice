package com.example.simbirsoftpracticeapp.presentation.help

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.common.Utils.customGetParcelable
import com.example.simbirsoftpracticeapp.databinding.FragmentHelpBinding
import com.example.simbirsoftpracticeapp.presentation.profile.ProfileFragment

class HelpFragment : BaseFragment(), HelpView {

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

    private var listState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHelpBinding.inflate(inflater, container, false).let {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listState = savedInstanceState?.customGetParcelable(LIST_STATE)
        initAdapter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE, listState)
    }

    private fun initAdapter() {
        categoryAdapter = CategoryAdapter(categories) {
            TODO("do on item click")
        }
        binding.rvCategories.adapter = categoryAdapter
        listState?.let {
            binding.rvCategories.layoutManager?.onRestoreInstanceState(listState)
        } ?: kotlin.run {
            listState = binding.rvCategories.layoutManager?.onSaveInstanceState()
        }
    }

    companion object {
        const val LIST_STATE = "LIST_STATE"
    }
}