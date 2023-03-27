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

class MyIntentService : IntentService(NAME_INTENT) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
        createNotificationChannel()
        val notification = createNotification()
        startForeground(ID_NOTIFICATION, notification)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, ID_CHANNEL)
            .setContentTitle("IntentService")
            .setContentText("IntentService")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                ID_CHANNEL,
                NAME_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        for (i in 0..5) {
            Thread.sleep(1000)
            log(i.toString())
        }
    }

    private fun log(message: String) {
        Log.d("Service_TAG", "MyIntentService: $message")
    }

    companion object {
        private const val ID_CHANNEL = "id_channel_intent"
        private const val ID_NOTIFICATION = 1
        private const val NAME_CHANNEL = "name_channel_intent"
        private const val NAME_INTENT = "MyIntentService"
        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}