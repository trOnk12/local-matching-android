package com.example.local_matching.worker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class LocationWorker(appContext: Context, workersParams: WorkerParameters) :
    CoroutineWorker(appContext, workersParams) {

    private val locationRetriever = LocationRetriever(appContext)

    override suspend fun doWork() = tryAndRethrow { locationRetriever.getUserLocation() }

    private suspend fun tryAndRethrow(tryBlock: suspend () -> Unit): Result {
        return try {
            tryBlock()

            Result.success()
        } catch (exception: Exception) {
            Result.failure()

            throw exception
        }
    }

}

class LocationWorkerManager(private val appContext: Context) {
    companion object {
        private const val LOCATION_PERIODIC_WORK_NAME = "location_periodic_work"
        private const val MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES: Long = 15
    }

    fun startPeriodRequest(
        requestInterval: RequestInterval = RequestInterval(
            MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES
        )
    ) {
        if (requestInterval.minutes < MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES) {
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

class LocationRetriever(private val appContext: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    suspend fun getUserLocation(): Location {
        checkRequiredPermissions()

        return getLocation()
    }

    private fun checkRequiredPermissions() {
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw Exception("This library required ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION")
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLocation() =
        suspendCancellableCoroutine<Location> { cancellableContinuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                cancellableContinuation.resume(location)
            }
        }

}

data class RequestInterval(
    val minutes: Long
)