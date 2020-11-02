package com.example.local_matching.location.retriever.android

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.example.local_matching.location.repository.model.LocationData
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("MissingPermission")
class AndroidLocationManager
@Inject constructor(
    criteria: Criteria,
    locationManager: LocationManager
) {
    companion object {
        private const val MIN_TIME_MS = 1000L
        private const val MIN_DISTANCE_M = 0F
    }

    var locationData: LocationData? = null
        private set

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationData = location.mapToLocationData()
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }
    }

    init {
        val locationProvider = locationManager.getBestProvider(criteria, true)

        if (locationProvider != null) {
            locationManager.requestLocationUpdates(
                locationProvider,
                MIN_TIME_MS,
                MIN_DISTANCE_M,
                locationListener
            )
        } else {
            Timber.e("No location provider found meeting the requirements")
        }
    }

}

fun Location.mapToLocationData() =
    LocationData(
        latitude = latitude,
        longitude = longitude
    )