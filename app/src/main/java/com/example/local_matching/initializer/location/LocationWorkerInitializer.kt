package com.example.local_matching.initializer.location

import android.content.Context
import com.example.local_matching.initializer.Initializer
import com.example.local_matching.location.worker.configuration.LocationWorkerConfiguration
import com.example.local_matching.location.worker.LocationWorkerManager
import com.example.local_matching.location.worker.configuration.RequestInterval
import javax.inject.Inject

class LocationWorkerInitializer : Initializer() {

    @Inject
    lateinit var locationWorkerManager: LocationWorkerManager

    override fun initialize(context: Context) {
        locationWorkerManager.enqueuePeriodicLocation{
            setRequestInterval(RequestInterval(minutes = 15))
        }
    }

}