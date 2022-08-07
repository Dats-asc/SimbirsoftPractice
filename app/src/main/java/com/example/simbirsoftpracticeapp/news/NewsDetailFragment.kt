package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsDetailBinding
import com.example.simbirsoftpracticeapp.news.data.CharityEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    private var eventId: Int? = null

    private var currentEvent: CharityEvent? = null

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
        getEvent()
    }

    private fun getEvent() {
        Utils.getEventsRxJava(requireContext().applicationContext)
            .map { it.events.find { event -> event.id == eventId } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                currentEvent = event
                setData()
            }
    }

    private fun setData() {

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