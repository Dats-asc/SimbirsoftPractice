package com.example.simbirsoftpracticeapp.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.Constants
import com.example.simbirsoftpracticeapp.Utils
import com.example.simbirsoftpracticeapp.data.CharityRepository
import com.example.simbirsoftpracticeapp.data.database.AppDatabase
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsDetailBinding
import com.example.simbirsoftpracticeapp.news.data.CharityEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    private var eventId: Int? = null

    private var currentEvent: CharityEvent? = null

    private val charityApi = CharityRepository().charityApi()

    private val db by lazy { AppDatabase(requireContext()) }

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
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        getEventFromDb()
    }

    private fun getEventFromDb() {
        db.charityEventsDao().getById(eventId ?: 0)
            .map { eventEntity -> Utils.mapCharityEventEntity(eventEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                binding.detailInfo.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }
            .doAfterSuccess {
                binding.detailInfo.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
            .doOnSubscribe {
                binding.progressBar.visibility = View.VISIBLE
                binding.detailInfo.visibility = View.INVISIBLE
            }
            .doAfterSuccess {
                binding.progressBar.visibility = View.GONE
                binding.detailInfo.visibility = View.VISIBLE
            }
            .subscribe({ event ->
                currentEvent = event
                setData()
            }, { e ->
                Log.e(this.tag, e.message.orEmpty())
                charityApi.getEventById(eventId ?: 0)
                    .map { events -> events.first() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ event ->
                        currentEvent = event
                        setData()
                    }, { e ->
                        Log.e(this.tag, e.message.orEmpty())
                    })
            })
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
                    phoneNumbers += if (i == event.phoneNumbers.count() - 1) {
                        event.phoneNumbers[i]
                    } else {
                        event.phoneNumbers[i] + "\n"
                    }
                }
                tvPhoneNumber.text = phoneNumbers
                if (event.membersCount - 5 > 0) {
                    tvMembersCount.text = "+${event.membersCount - 5}"
                }
            }
        }
    }

}