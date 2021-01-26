package com.ljchen17.annoyingex.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ljchen17.annoyingex.MainActivity
import com.ljchen17.annoyingex.R
import kotlin.random.Random
import java.util.*

class NotificationPostWorker(private val context: Context, workParams: WorkerParameters): Worker(context , workParams) {

    override fun doWork(): Result {
        val notificationManagerCompat = NotificationManagerCompat.from(context)

        createMessageChannel(notificationManagerCompat)

        val apiManager = (context.applicationContext as MessageApplication).apiManager
        var getMessage = apiManager.getMessageList()

        var messgaeToSend = getMessage!!.messages.shuffled().take(1)[0]

        val mainIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        mainIntent.putExtra(MESSAGE_TEXT_ID, messgaeToSend)

            val pendingMainIntent = PendingIntent.getActivity(
                context,
                0,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


        val notification = NotificationCompat.Builder(context, MESSAGE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Regina George")
            .setContentText(messgaeToSend)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingMainIntent)
            .setAutoCancel(true)
            .build()

        notificationManagerCompat.notify(Random.nextInt(), notification)

        return Result.success()
    }

    private fun createMessageChannel(notificationManagerCompat:NotificationManagerCompat) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Message Notifications"
            val descriptionText = "All Msgs from Ex"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(MESSAGE_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    companion object {
        const val MESSAGE_CHANNEL_ID = "MESSAGECHANNELID"
        const val MESSAGE_TEXT_ID = "MESSAGETEXTID"
    }
}