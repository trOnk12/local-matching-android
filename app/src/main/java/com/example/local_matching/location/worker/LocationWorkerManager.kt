package com.example.local_matching.location.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.local_matching.location.worker.configuration.LocationWorkerConfiguration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES: Long = 15

class LocationWorkerManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val LOCATION_PERIODIC_WORK_NAME = "location_periodic_work"
    }

    fun enqueuePeriodicLocation(build: LocationWorkerConfiguration.Builder.() -> Unit) {
        val locationWorkerConfiguration = buildLocationWorkerConfiguration(build)

        val periodWorkRequest = buildPeriodicWorkRequest(locationWorkerConfiguration)

        enqueuePeriodicWork(periodWorkRequest)
    }

    private fun buildLocationWorkerConfiguration(build: LocationWorkerConfiguration.Builder.() -> Unit) =
        LocationWorkerConfiguration.Builder().apply(build).build()

    private fun enqueuePeriodicWork(periodicWorkRequest: PeriodicWorkRequest) {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            LOCATION_PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private fun buildPeriodicWorkRequest(
        locationWorkerConfiguration: LocationWorkerConfiguration
    ): PeriodicWorkRequest {
        with(locationWorkerConfiguration) {
            return PeriodicWorkRequestBuilder<LocationWorker>(
                requestInterval.minutes,
                TimeUnit.MINUTES
            ).build()
        }
    }

}
