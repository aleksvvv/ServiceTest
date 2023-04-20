package com.bignerdranch.android.servicetest

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorkManager(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)

        for (i in 0..5) {
            Thread.sleep(1000)
            log("Timer - $i page: $page")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("Service_TAG", "MyWorkManager: $message")
    }

    companion object {
        private const val PAGE = "page"
        const val NAME_WORK = "name work"
        fun workRequest(page: Int): OneTimeWorkRequest = OneTimeWorkRequestBuilder<MyWorkManager>()
            .setInputData(workDataOf(PAGE to page))
            .setConstraints(setConstraint())
            .build()

        private fun setConstraint() =
            Constraints.Builder().setRequiresCharging(true).build()
    }

}