package com.ljchen17.annoyingex.data

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class NotificationWorkManager (private val context: Context){

        private var workManager = WorkManager.getInstance(context)

        fun startTheNotification() {
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<NotificationPostWorker>(20, TimeUnit.MINUTES)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build()

            workManager.enqueue(workRequest)
        }

    fun endTheNotification() {
        workManager.cancelAllWork()
    }
    }