package com.bignerdranch.android.servicetest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
coroutine.launch {
            for (i in 0..100) {
                delay(1000)
            log(i.toString())
                stopSelf()
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
        Log.d("Service_TAG", "MyService: $message")
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}