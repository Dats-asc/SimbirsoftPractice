package com.example.simbirsoftpracticeapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.common.Utils
import com.example.simbirsoftpracticeapp.databinding.FragmentNewsDetailBinding
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.common.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class NewsDetailFragment : BaseFragment(), NewsDetailView {

    private lateinit var binding: FragmentNewsDetailBinding

    private var eventId: Int? = null

    private var event: CharityEvent? = null

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
            findNavController().navigateUp()
        }
        presenter.getEvent(eventId ?: 0)

        binding.helpWithMoney.setOnClickListener {
            val dialog = DonationAlertDialog(
                requireContext(),
                object : DonationAlertDialog.Listener {
                    override fun onSend(amountOfMoney: Double) {
                        val constraints = Constraints.Builder()
                            .setRequiresCharging(true)
                            .build()

                        val data = Data.Builder()
                            .putInt(Constants.EVENT_ID, eventId ?: 0)
                            .putString(Constants.EVENT_NAME, event?.title)
                            .putDouble(Constants.DONATION_AMOUNT, amountOfMoney)
                            .putInt(Constants.NOTIFICATION_MODE, DonationPushNotificationWorker.NotifyMode.REGULAR.ordinal)
                            .build()
                        val workRequest =
                            OneTimeWorkRequest.Builder(DonationPushNotificationWorker::class.java)
                                .setConstraints(constraints)
                                .setInputData(data)
                                .build()
                        WorkManager.getInstance(requireActivity()).enqueue(workRequest)
                    }
                }
            )

            dialog.showDialog()
        }
    }

    override fun setEventDetail(event: CharityEvent) {
        this.event = event
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
                val a = 0
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