package com.example.local_matching.core

import androidx.work.ListenableWorker

suspend fun tryAndRethrow(tryBlock: suspend () -> Unit): ListenableWorker.Result {
    return try {
        tryBlock()

        ListenableWorker.Result.success()
    } catch (exception: Exception) {
        ListenableWorker.Result.failure()

        throw exception
    }
}