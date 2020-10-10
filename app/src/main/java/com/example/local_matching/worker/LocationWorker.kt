package com.example.local_matching.worker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class LocationWorker(appContext: Context, workersParams: WorkerParameters) :
    CoroutineWorker(appContext, workersParams) {

    private val locationRetriever = LocationRetriever(appContext)

    override suspend fun doWork(): Result {
        Log.d("TEST", "location is ${locationRetriever.getUserLocation()}")

        return Result.success()
    }

}

class LocationWorkerManager(private val appContext: Context) {
    companion object {
        private const val LOCATION_PERIODIC_WORK_NAME = "location_periodic_work"
        private const val MINIMUM_PERIODIC_TIME_INTERVAL: Long = 15
    }

    fun startPeriodRequest(
        requestInterval: RequestInterval = RequestInterval(
            MINIMUM_PERIODIC_TIME_INTERVAL
        )
    ) {
        if (requestInterval.minutes < MINIMUM_PERIODIC_TIME_INTERVAL) {
            enqueuePeriodicWork(buildPeriodicWorkRequest(requestInterval))
        } else {
            enqueuePeriodicWork(buildPeriodicWorkRequest(requestInterval))
        }
    }

    private fun buildPeriodicWorkRequest(requestInterval: RequestInterval) =
        PeriodicWorkRequestBuilder<LocationWorker>(
            requestInterval.minutes,
            TimeUnit.MINUTES
        ).build()

    private fun enqueuePeriodicWork(periodicWorkRequest: PeriodicWorkRequest) {
        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
            LOCATION_PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

}

class LocationRetriever(appContext: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    @SuppressLint("MissingPermission")
    suspend fun getUserLocation() = suspendCancellableCoroutine<Location> { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                continuation.resume(location!!)
            }
    }

}

data class RequestInterval(
    val minutes: Long
)