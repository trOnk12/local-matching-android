package com.example.local_matching.location.repository

import android.Manifest
import android.os.Build
import com.example.local_matching.location.retriever.ILocationRetriever
import com.example.local_matching.utils.PermissionChecker
import com.example.local_matching.utils.PermissionStatus
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val locationRetriever : ILocationRetriever
) {

    suspend fun getLocation() =
        when(permissionChecker.checkPermissions(getRequiredPermissions())){
            is PermissionStatus.Granted -> locationRetriever.getLocation()
            is PermissionStatus.Denied -> throw Exception("No permission granted to retrieve user location")
        }

    private fun getRequiredPermissions() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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