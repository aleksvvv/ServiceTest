package com.bignerdranch.android.servicetest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            coroutine.launch {
                var workerItem = params?.dequeueWork()

                while (workerItem != null) {
                    val page = workerItem.intent?.getIntExtra(PAGE, 0)

                    for (i in 0..4) {
                        delay(1000)
                        log("Timer - $i page: $page")

                    }
                    params?.completeWork(workerItem)
                    workerItem = params?.dequeueWork()
                }
                jobFinished(params, false)
            }
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

    companion object {
        const val ID_JOB = 1111
        private const val PAGE = "page"
        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }

}