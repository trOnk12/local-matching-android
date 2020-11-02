package com.example.local_matching.location.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.local_matching.core.tryAndRethrow
import com.example.local_matching.location.repository.LocationRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class LocationWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workersParams: WorkerParameters,
    private val locationRepository: LocationRepository
) : CoroutineWorker(appContext, workersParams) {

    override suspend fun doWork() = tryAndRethrow {
        val locationData = locationRepository.getLocation()

        if (locationData == null) {
            Timber.i("No location found yet!")
        } else {
            Timber.d("Location found: $locationData")
        }
    }

    class Factory @Inject constructor(
        private val locationRepository: Provider<LocationRepository>
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return LocationWorker(appContext, params, locationRepository.get())
        }

    }

}

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): CoroutineWorker
}


