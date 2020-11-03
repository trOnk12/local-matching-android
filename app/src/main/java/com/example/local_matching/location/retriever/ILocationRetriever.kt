package com.example.local_matching.location.retriever

import com.example.local_matching.model.LocationData

interface ILocationRetriever {
    fun getLocation() : LocationData?
}