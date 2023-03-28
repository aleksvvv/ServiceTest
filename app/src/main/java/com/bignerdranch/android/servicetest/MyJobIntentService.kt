package com.bignerdranch.android.servicetest

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        val page = intent.getIntExtra(PAGE, 0)
        log("onHandleWork")
        for (i in 0..5) {
            Thread.sleep(1000)
            log("Timer - $i page: $page")
        }
    }

    private fun log(message: String) {
        Log.d("Service_TAG", "MyJobIntentService: $message")
    }

    companion object {
        private const val PAGE = "page"
        private const val ID_JOB = 23

        fun enqueue(context: Context, page: Int) {
            JobIntentService.enqueueWork(
                context,
                MyJobIntentService::class.java,
                ID_JOB,
                newIntent(context,page)
                )
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}