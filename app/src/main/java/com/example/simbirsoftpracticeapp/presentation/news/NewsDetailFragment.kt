package com.example.simbirsoftpracticeapp.presentation.news

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.common.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsDetailBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.common.BaseFragment
import com.example.simbirsoftpracticeapp.presentation.profile.ProfileAlertDialogView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NewsDetailFragment : BaseFragment(), NewsDetailView {

    private lateinit var binding: FragmentNewsDetailBinding

    private var eventId: Int? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsDetailPresenter

    @ProvidePresenter
    fun providePresenter(): NewsDetailPresenter = presenter

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
        presenter.getEvent(eventId ?: 0)

        binding.helpWithMoney.setOnClickListener {
            val dialog = DonationAlertDialog(
                requireContext(),
                object : DonationAlertDialog.Listener {
                    override fun onSend(amount: Double) {
                        TODO("")
                    }
                }
            )

            dialog.showDialog()
        }
    }

    override fun setEventDetail(event: CharityEvent) {
        with(binding) {
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

    override fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.detailInfo.visibility = View.INVISIBLE
    }

    override fun hideProgressbar() {
        binding.progressBar.visibility = View.GONE
        binding.detailInfo.visibility = View.VISIBLE
    }

}