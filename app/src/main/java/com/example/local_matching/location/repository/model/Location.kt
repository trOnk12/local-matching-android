package com.example.local_matching.location.repository.model

import android.location.Location

data class LocationData(val latitude: Double, val longitude: Double)

fun Location.mapToLocationData(): LocationData {
    return LocationData(
        latitude = latitude,
        longitude = longitude
    )
}