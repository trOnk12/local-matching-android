package com.example.local_matching.location.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.local_matching.location.worker.configuration.LocationWorkerConfiguration
import com.example.local_matching.location.worker.configuration.RequestInterval
import java.util.concurrent.TimeUnit

const val MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES: Long = 15

object LocationWorkerManager {
    private const val LOCATION_PERIODIC_WORK_NAME = "location_periodic_work"

    operator fun invoke(context: Context, locationWorkerConfiguration: LocationWorkerConfiguration){
        with(locationWorkerConfiguration) {
            enqueuePeriodicWork(context, buildPeriodicWorkRequest(requestInterval))
        }
    }

    private fun enqueuePeriodicWork(
        appContext: Context,
        periodicWorkRequest: PeriodicWorkRequest
    ) {
        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
            LOCATION_PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private fun buildPeriodicWorkRequest(
        requestInterval: RequestInterval
    ) =
        PeriodicWorkRequestBuilder<LocationWorker>(
            requestInterval.minutes,
            TimeUnit.MINUTES
        ).build()

}