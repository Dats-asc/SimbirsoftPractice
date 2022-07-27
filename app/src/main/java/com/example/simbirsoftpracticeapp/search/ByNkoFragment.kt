package com.example.simbirsoftpracticeapp.search

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.databinding.FragmentByNkoBinding
import kotlin.random.Random

class ByNkoFragment : Fragment() {

    private lateinit var binding: FragmentByNkoBinding

    private val searchResults = listOf<SearchResult>(
        SearchResult(0, "Благотворительный фонд Алины Кабаевой"),
        SearchResult(1, "Благотворительный Фонд В. Потанина"),
        SearchResult(2, "\"Во имя жизни\""),
        SearchResult(3, "\"Детские домики\""),
        SearchResult(4, "\"Мозаика счастья\""),
        SearchResult(5, "Фонд 1"),
        SearchResult(6, "Фонд 2"),
        SearchResult(7, "Фонд 3"),
        SearchResult(8, "Фонд 4")
    )

    private var resultsAdapter: ResultsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentByNkoBinding.inflate(inflater, container, false).let {
        binding = FragmentByNkoBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    private fun initAdapter() {
        val dataset = arrayListOf<SearchResult>()
        for (i in 0..Random.nextInt(searchResults.size)) {
            dataset.add(searchResults[Random.nextInt(searchResults.size)])
        }
        resultsAdapter = ResultsAdapter(dataset) {
            //TODO on search result item clicked
        }
        binding.rvSearchResults.adapter = resultsAdapter

        binding.rvSearchResults.addItemDecoration(
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
}