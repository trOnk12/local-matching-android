package com.example.local_matching.initializer.workmanager

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.local_matching.initializer.ObjectInitializer
import javax.inject.Inject


class WorkerMangerInitializer : ObjectInitializer() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun initializeObject(context: Context) {
        initializeWorkerManager(context, getWorkerManagerConfiguration(workerFactory))
    }

    private fun initializeWorkerManager(context: Context, configuration: Configuration) {
            WorkManager.initialize(
                context.applicationContext,
                configuration
            )
        }

    private fun getWorkerManagerConfiguration(workerFactory: WorkerFactory): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

}