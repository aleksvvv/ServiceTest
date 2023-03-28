package com.bignerdranch.android.servicetest

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyIntentService2 : IntentService(NAME_INTENT) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)

    }

    override fun onHandleIntent(intent: Intent?) {
        val page = intent?.getIntExtra(PAGE,0) ?: 0
        log("onHandleIntent")
        for (i in 0..5) {
            Thread.sleep(1000)
            log("Timer - $i page: $page")
        }
    }

    private fun log(message: String) {
        Log.d("Service_TAG", "MyIntentService2: $message")
    }

    companion object {
        private const val NAME_INTENT = "MyIntentService2"
        private const val PAGE = "page"
        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE,page)
            }
        }
    }
}