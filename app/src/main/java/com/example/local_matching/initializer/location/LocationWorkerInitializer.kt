package com.example.local_matching.initializer.location

import android.content.Context
import com.example.local_matching.initializer.ObjectInitializer
import com.example.local_matching.location.worker.configuration.LocationWorkerConfiguration
import com.example.local_matching.location.worker.LocationWorkerManager
import com.example.local_matching.location.worker.configuration.RequestInterval

class LocationWorkerInitializer : ObjectInitializer() {

    override fun initializeObject(context: Context) {
        LocationWorkerManager(
            context.applicationContext,
            getLocationWorkerConfiguration()
        )
    }

    private fun getLocationWorkerConfiguration(): LocationWorkerConfiguration {
        return LocationWorkerConfiguration.Builder()
            .setRequestInterval(RequestInterval(minutes = 15))
            .build()
    }

}