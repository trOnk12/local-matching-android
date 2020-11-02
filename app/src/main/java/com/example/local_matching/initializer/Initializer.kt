package com.example.local_matching.initializer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri

abstract class Initializer : ContentProvider() {

    override fun onCreate(): Boolean {
        if (context != null) {
            initialize(context!!)
        }

        return true
    }

    abstract fun initialize(context: Context)

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
            throw NullPointerException("${javaClass.canonicalName} ProviderInfo cannot be null.");
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