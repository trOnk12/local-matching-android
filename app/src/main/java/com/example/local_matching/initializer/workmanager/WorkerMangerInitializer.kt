package com.example.local_matching.initializer.workmanager

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.local_matching.initializer.Initializer
import javax.inject.Inject


class WorkerMangerInitializer : Initializer() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun initialize(context: Context) {
        val configuration = getWorkerManagerConfiguration(workerFactory)

        initializeWorkerManager(context, configuration)
    }

    private fun getWorkerManagerConfiguration(workerFactory: WorkerFactory) =
        Configuration.Builder().setWorkerFactory(workerFactory).build()

    private fun initializeWorkerManager(context: Context, configuration: Configuration) {
        WorkManager.initialize(
            context.applicationContext,
            configuration
        )
    }

}