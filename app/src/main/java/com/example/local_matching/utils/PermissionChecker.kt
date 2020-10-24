package com.example.local_matching.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.local_matching.location.PermissionStatus

class PermissionChecker(private val appContext: Context) {

    fun checkPermissions(vararg permissions: List<String>): PermissionStatus {
        permissions.forEach { permission ->
            if (checkPermission(permission) == com.example.local_matching.utils.PermissionStatus.Denied) {
                return com.example.local_matching.utils.PermissionStatus.Denied
            }
        }

        return com.example.local_matching.utils.PermissionStatus.Granted
    }

    private fun checkPermission(permission: String): PermissionStatus {
        return if (ActivityCompat.checkSelfPermission(
                appContext,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            com.example.local_matching.utils.PermissionStatus.Denied
        } else {
            com.example.local_matching.utils.PermissionStatus.Granted
        }
    }

}


sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
}