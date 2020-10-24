package com.example.local_matching.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import com.example.local_matching.utils.PermissionChecker
import com.example.local_matching.utils.PermissionStatus
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRetriever(appContext: Context) {

    private val requiredPermissions: List<String>
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                )
            } else {
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            }
        }

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    private val permissionChecker = PermissionChecker(appContext)

    suspend fun getUserLocation(): Location {
        when (permissionChecker.checkPermissions(requiredPermissions)) {
            is PermissionStatus.Granted -> return getLocation().also { location ->
                Log.d(
                    "TEST",
                    "user location: $location"
                )
            }
            is PermissionStatus.Denied -> throw Exception("This library required ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION")
        }
    }
    @SuppressLint("MissingPermission")
    private suspend fun getLocation() =
        suspendCancellableCoroutine<Location> { cancellableContinuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                cancellableContinuation.resume(location)
            }.addOnFailureListener { exception ->
                cancellableContinuation.resumeWithException(exception)
            }.addOnCanceledListener { cancellableContinuation.cancel() }
        }

}

