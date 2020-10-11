package com.example.local_matching.location

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class LocationWorkerManager private constructor() {
    companion object {

        private const val LOCATION_PERIODIC_WORK_NAME = "location_periodic_work"

        internal const val MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES: Long = 15

        internal fun initialize(
            appContext: Context,
            locationWorkerConfiguration: LocationWorkerConfiguration
        ) {
            startPeriodRequest(appContext, locationWorkerConfiguration)
        }

        private fun startPeriodRequest(
            appContext: Context,
            locationWorkerConfiguration: LocationWorkerConfiguration
        ) {
            with(locationWorkerConfiguration) {
                if (requestInterval.minutes < MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES) {
                    enqueuePeriodicWork(appContext, buildPeriodicWorkRequest(requestInterval))
                } else {
                    enqueuePeriodicWork(appContext, buildPeriodicWorkRequest(requestInterval))
                }
            }
        }

        private fun buildPeriodicWorkRequest(
            requestInterval: RequestInterval
        ) =
            PeriodicWorkRequestBuilder<LocationWorker>(
                requestInterval.minutes,
                TimeUnit.MINUTES
            ).build()

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
    }

}
