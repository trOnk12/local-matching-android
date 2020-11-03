package com.example.local_matching.repository

import android.Manifest
import android.os.Build
import com.example.local_matching.location.retriever.ILocationRetriever
import com.example.local_matching.model.LocationData
import com.example.local_matching.network.LocalMatchingAPI
import com.example.local_matching.utils.PermissionChecker
import com.example.local_matching.utils.PermissionStatus
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val locationRetriever: ILocationRetriever,
    private val localMatchingAPI: LocalMatchingAPI
) {

    fun getLocation() =
        when (permissionChecker.checkPermissions(getRequiredPermissions())) {
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

    fun sendLocation(userId: String, locationData: LocationData) {
        localMatchingAPI.sendLocation(userId, locationData)
    }

}