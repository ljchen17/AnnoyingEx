package com.ljchen17.annoyingex.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ljchen17.annoyingex.data.model.AllMessages

class ApiManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    private var allMessages: AllMessages? = null

    fun fetchMessagesList(onMessagesReady: (AllMessages) -> Unit, onError: (() -> Unit)? = null) {
        val messageURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"

        val request = StringRequest(
            Request.Method.GET, messageURL,
            { response ->
                // Success
                val gson = Gson()
                val messagesFetched = gson.fromJson(response, AllMessages::class.java )
                allMessages = messagesFetched
                onMessagesReady(messagesFetched)
            },
            {
                onError?.invoke()
            }
        )
        queue.add(request)
    }

    fun getMessageList(): AllMessages? {
        return allMessages
    }
}