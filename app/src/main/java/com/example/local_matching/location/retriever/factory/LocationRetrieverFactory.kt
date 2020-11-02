package com.example.local_matching.location.retriever.factory

import com.example.local_matching.location.retriever.ILocationRetriever
import com.example.local_matching.location.retriever.android.LocationRetrieverAndroid
import com.example.local_matching.location.retriever.googleservice.LocationRetrieverGoogleService
import com.example.local_matching.utils.GooglePlayServiceUtils
import javax.inject.Inject


class LocationRetrieverFactory
@Inject constructor(
    private val googlePlayServiceUtils: GooglePlayServiceUtils
) {

    fun create(): ILocationRetriever {
        return if (googlePlayServiceUtils.isAvailable()) {
            LocationRetrieverGoogleService()
        } else {
            LocationRetrieverAndroid()
        }
    }

}