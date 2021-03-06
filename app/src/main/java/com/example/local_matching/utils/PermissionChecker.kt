package com.example.local_matching.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class PermissionChecker
@Inject constructor(
    private val appContext: Context
) {

    fun checkPermissions(permissions: List<String>): PermissionStatus {
        permissions.forEach { permission ->
            if (checkPermission(permission) == PermissionStatus.Denied) {
                return PermissionStatus.Denied
            }
        }

        return PermissionStatus.Granted
    }

    private fun checkPermission(permission: String): PermissionStatus {
        return if (ActivityCompat.checkSelfPermission(appContext, permission) == PackageManager.PERMISSION_GRANTED) {
            PermissionStatus.Granted
        } else {
            PermissionStatus.Denied
        }
    }

}

sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
}