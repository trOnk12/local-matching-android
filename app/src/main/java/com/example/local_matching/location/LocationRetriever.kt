package com.example.local_matching.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

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