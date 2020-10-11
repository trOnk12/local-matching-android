package com.example.local_matching.initializer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.local_matching.location.LocationWorkerConfiguration
import com.example.local_matching.location.LocationWorkerManager


class WorkerMangerInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        if (context != null) {
            initializeWorkerManager(context!!)
            initializeLocationWorkerManager(context!!)
        }

        return true
    }

    private fun initializeLocationWorkerManager(context: Context) {
        LocationWorkerConfiguration.Builder().build().let { locationWorkerConfiguration ->
            LocationWorkerManager.initialize(
                context.applicationContext,
                locationWorkerConfiguration
            )
        }
    }

    private fun initializeWorkerManager(context: Context) {
        Configuration.Builder().build().let { configuration ->
            WorkManager.initialize(context.applicationContext, configuration)
        }
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return null
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        checkIfProvidedInfoIsCorrect(info)

        super.attachInfo(context, info)
    }

    private fun checkIfProvidedInfoIsCorrect(info: ProviderInfo?) {
        if (info == null) {
            throw NullPointerException("YourLibraryInitProvider ProviderInfo cannot be null.");
        }
        // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
        if ("com.example.local_matching.yourlibraryinitprovider" == info.authority) {
            throw IllegalStateException(
                "Incorrect provider authority in manifest. Most likely due to a "
                        + "missing applicationId variable in application\'s build.gradle."
            )
        }
    }

}