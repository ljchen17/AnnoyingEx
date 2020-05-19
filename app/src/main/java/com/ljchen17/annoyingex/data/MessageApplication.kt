package com.ljchen17.annoyingex.data
import android.app.Application

class MessageApplication: Application() {
    lateinit var notificationWorkManager: NotificationWorkManager
        private set

    lateinit var apiManager: ApiManager

    override fun onCreate () {
        super.onCreate()
        apiManager = ApiManager(this)
        notificationWorkManager = NotificationWorkManager(this)
    }
}