package com.example.local_matching.location

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LocationWorker(appContext: Context, workersParams: WorkerParameters) :
    CoroutineWorker(appContext, workersParams) {

    private val locationRetriever = LocationRetriever(appContext)

    override suspend fun doWork() = tryAndRethrow {
        locationRetriever.getUserLocation()
    }

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

