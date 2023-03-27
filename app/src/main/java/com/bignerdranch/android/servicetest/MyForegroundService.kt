package com.bignerdranch.android.servicetest

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

class MyForegroundService:Service() {

    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")

        createNotificationChannel()

        val notification = createNotification()

        startForeground(ID_NOTIFICATION,notification)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, ID_CHANNEL)
            .setContentTitle("ForegroundService")
            .setContentText("ForegroundService")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                ID_CHANNEL,
                NAME_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutine.launch {
            for (i in 0..100) {
                delay(1000)
                log(i.toString())
            }
        }

//        return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutine.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("Service_TAG", "MyForegroundService: $message")
    }

    companion object {
        private const val ID_CHANNEL = "id_channel_foreground"
        private const val ID_NOTIFICATION = 1
        private const val NAME_CHANNEL = "name_channel_foreground"
        fun newIntent(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }
    }

}