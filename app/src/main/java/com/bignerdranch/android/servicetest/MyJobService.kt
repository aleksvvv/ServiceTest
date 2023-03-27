package com.bignerdranch.android.servicetest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobService: JobService() {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        coroutine.launch {
            for (i in 0..100) {
                delay(1000)
                log(i.toString())
                stopSelf()
            }
            jobFinished(params,true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutine.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("Service_TAG", "MyJobService: $message")
    }
    companion object{
        const val ID_JOB = 1111
    }

}