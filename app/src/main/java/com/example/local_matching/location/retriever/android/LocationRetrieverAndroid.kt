package com.example.local_matching.location.retriever.android

import com.example.local_matching.location.retriever.ILocationRetriever
import javax.inject.Inject

class LocationRetrieverAndroid
@Inject constructor(
    private val androidLocationManager: AndroidLocationManager
) : ILocationRetriever {

    override fun getLocation() = androidLocationManager.locationData

}