package com.example.simbirsoftpracticeapp.presentation.news

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

        val notifyMode = NotifyMode.values()[inputData.getInt(Constants.NOTIFICATION_MODE, 0)]

        when (notifyMode) {
            NotifyMode.FIRST -> {
                regularNotification()
            }
            NotifyMode.REMINDER -> {
                remindNotification()
            }
        }

        return Result.success()
    }

    private fun regularNotification() {

        val intent = Intent(appContext, MainActivity::class.java)
            .putExtra(Constants.EVENT_ID, inputData.getInt(Constants.EVENT_ID, -1))
        val pendingIntent = PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val remindIntent = Intent(appContext, RemindLaterBroadcastReceiver::class.java)
            .putExtra(Constants.EVENT_ID, inputData.getInt(Constants.EVENT_ID, 0))
            .putExtra(Constants.EVENT_NAME, inputData.getString(Constants.EVENT_NAME))
            .putExtra(Constants.EVENT_ID, inputData.getDouble(Constants.DONATION_AMOUNT, 0.0))
            .putExtra(Constants.NOTIFICATION_MODE, inputData.getInt(Constants.NOTIFICATION_MODE, 0))

        val remindPendingIntent = PendingIntent.getBroadcast(appContext, 0, remindIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder =
            NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_icon2)
                .setContentTitle(inputData.getString(Constants.EVENT_NAME))
                .setContentText(
                    "Спасибо, что пожертвовали ${
                        inputData.getDouble(
                            Constants.DONATION_AMOUNT,
                            0.0
                        )
                    } ₽! Будем очень признательны, если вы сможете пожертвовать еще больше."
                )
                .addAction(R.mipmap.ic_icon2, "Напомнить позже", remindPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(appContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Donation notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder.setChannelId(CHANNEL_ID)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun remindNotification() {
        val intent = Intent(appContext, MainActivity::class.java)
            .putExtra(Constants.EVENT_ID, inputData.getInt(Constants.EVENT_ID, 0))

        val pendingIntent =
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_MUTABLE)

        val notificationBuilder =
            NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_icon2)
                .setContentTitle(inputData.getString(Constants.EVENT_NAME))
                .setContentText("Напоминаем, что мы будем очень признательны, если вы сможете пожертвовать еще больше.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val notificationManager = NotificationManagerCompat.from(appContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Donation notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder.setChannelId(CHANNEL_ID)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    class RemindLaterBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            NotificationManagerCompat.from(p0!!).cancel(null, NOTIFICATION_ID)
            val data = Data.Builder()
                .putInt(Constants.EVENT_ID, p1?.extras?.getInt(Constants.EVENT_ID) ?: -1)
                .putString(Constants.EVENT_NAME, p1?.extras?.getString(Constants.EVENT_NAME))
                .putDouble(Constants.DONATION_AMOUNT, p1?.extras?.getDouble(Constants.DONATION_AMOUNT) ?: 0.0)
                .putInt(Constants.NOTIFICATION_MODE, DonationPushNotificationWorker.NotifyMode.REMINDER.ordinal)
                .build()
            val scheduleWork = OneTimeWorkRequest.Builder(DonationPushNotificationWorker::class.java)
                .setInputData(data)
                .setInitialDelay(30, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(p0!!).enqueue(scheduleWork)
        }

    }

    enum class NotifyMode {
        FIRST,
        REMINDER
    }

    companion object {
        const val CHANNEL_ID = "notify_001"
        const val NOTIFICATION_ID = 0
    }
}