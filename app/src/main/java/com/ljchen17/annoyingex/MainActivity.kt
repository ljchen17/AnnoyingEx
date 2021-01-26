package com.ljchen17.annoyingex

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ljchen17.annoyingex.data.MessageApplication
import com.ljchen17.annoyingex.data.NotificationPostWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Grab data from Intent's Extras
        val messageShowed = intent.getStringExtra(NotificationPostWorker.MESSAGE_TEXT_ID)

        if (messageShowed != null) {
            findViewById<TextView>(R.id.messageShowed).text = "Last Message Details:\n" + messageShowed
        }

        val apiManager = (application as MessageApplication).apiManager
        apiManager.fetchMessagesList ({ allMessages ->

            button1.setOnClickListener {
                (application as MessageApplication).notificationWorkManager.startTheNotification()
            }
        },
            {
                Toast.makeText(this, "Error fetching messages ", Toast.LENGTH_SHORT).show()
            })

        button2.setOnClickListener {
            (application as MessageApplication).notificationWorkManager.endTheNotification()
        }
    }
}
