package com.example.simbirsoftpracticeapp.presentation.news

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.*
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.presentation.main.MainActivity
import java.util.concurrent.TimeUnit


class DonationPushNotificationWorker(
    private val appContext: Context,
    workRequest: WorkerParameters
) : Worker(appContext, workRequest) {

    override fun doWork(): Result {
        return try {
            when (NotifyMode.values()[inputData.getInt(Constants.NOTIFICATION_MODE, 0)]) {
                NotifyMode.REGULAR -> {
                    regularNotification()
                }
                NotifyMode.REMINDER -> {
                    remindNotification()
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun regularNotification() {
        val remindIntent = Intent(appContext, RemindLaterBroadcastReceiver::class.java)
            .putExtra(Constants.EVENT_ID, inputData.getInt(Constants.EVENT_ID, 0))
            .putExtra(Constants.EVENT_NAME, inputData.getString(Constants.EVENT_NAME))
            .putExtra(
                Constants.DONATION_AMOUNT,
                inputData.getDouble(Constants.DONATION_AMOUNT, 0.0)
            )
            .putExtra(Constants.NOTIFICATION_MODE, inputData.getInt(Constants.NOTIFICATION_MODE, 0))

        val remindPendingIntent =
            PendingIntent.getBroadcast(appContext, 0, remindIntent, PendingIntent.FLAG_IMMUTABLE)

        val deepLink = NavDeepLinkBuilder(appContext).apply {
            setGraph(R.navigation.nav_graph)
            setDestination(R.id.newsDetailFragment)
            setArguments(bundleOf(Constants.EVENT_ID to inputData.getInt(Constants.EVENT_ID, -1)))
            setComponentName(MainActivity::class.java)
        }.createPendingIntent()

        val notification =
            NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(deepLink)
                .setSmallIcon(R.mipmap.ic_icon2)
                .setContentTitle(inputData.getString(Constants.EVENT_NAME))
                .setContentText(
                    appContext.getString(
                        R.string.notification_msg,
                        inputData.getDouble(Constants.DONATION_AMOUNT, 0.0).toString()
                    )
                )
                .addAction(R.mipmap.ic_icon2, "Напомнить позже", remindPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        showNotification(notification)
    }

    private fun remindNotification() {
        val deepLink = NavDeepLinkBuilder(appContext).apply {
            setGraph(R.navigation.nav_graph)
            setDestination(R.id.newsDetailFragment)
            setArguments(bundleOf(Constants.EVENT_ID to inputData.getInt(Constants.EVENT_ID, -1)))
            setComponentName(MainActivity::class.java)
        }.createPendingIntent()

        val notification =
            NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(deepLink)
                .setSmallIcon(R.mipmap.ic_icon2)
                .setContentTitle(inputData.getString(Constants.EVENT_NAME))
                .setContentText(appContext.getString(R.string.notification_remind_msg))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        showNotification(notification)
    }

    private fun showNotification(notification: Notification) {
        val notificationManager = NotificationManagerCompat.from(appContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Charity notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    enum class NotifyMode {
        REGULAR,
        REMINDER
    }

    class RemindLaterBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            NotificationManagerCompat.from(p0!!).cancel(null, NOTIFICATION_ID)
            val data = Data.Builder()
                .putInt(Constants.EVENT_ID, p1?.extras?.getInt(Constants.EVENT_ID) ?: -1)
                .putString(Constants.EVENT_NAME, p1?.extras?.getString(Constants.EVENT_NAME))
                .putDouble(
                    Constants.DONATION_AMOUNT,
                    p1?.extras?.getDouble(Constants.DONATION_AMOUNT) ?: 0.0
                )
                .putInt(
                    Constants.NOTIFICATION_MODE,
                    NotifyMode.REMINDER.ordinal
                )
                .build()
            val scheduleWork =
                OneTimeWorkRequest.Builder(DonationPushNotificationWorker::class.java)
                    .setInputData(data)
                    .setInitialDelay(30, TimeUnit.MINUTES)
                    .build()
            WorkManager.getInstance(p0).enqueue(scheduleWork)
        }
    }

    companion object {
        const val CHANNEL_ID = "charity_notification"
        const val NOTIFICATION_ID = 0
    }
}