package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    private var eventId: Int? = null

    private val currentEvent by lazy {
        val events = Utils.getEvents(requireContext().applicationContext)
        events.events.find { event -> event.id == eventId }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsDetailBinding.inflate(inflater, container, false).let {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        arguments?.let {
            eventId = it.getInt(Constants.EVENT_ID)
        }
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            currentEvent?.let { event ->
                toolbar.title = event.title
                tvEventTitle.text = event.title
                tvEndDate.text = Utils.getFormatedDate(event.date)
                tvAddress.text = event.address
                tvEventDescription.text = event.description
                var phoneNumbers = ""
                for (i in 0 until event.phoneNumbers.count()) {
                    if (i == event.phoneNumbers.count() - 1) {
                        phoneNumbers += event.phoneNumbers[i]
                    } else {
                        phoneNumbers += event.phoneNumbers[i] + "\n"
                    }
                }
                tvPhoneNumber.text = phoneNumbers
                if (event.membersCount - 5 > 0) {
                    tvMembersCount.text = "+${event.membersCount - 5}"
                }
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

}