package com.example.local_matching.location.retriever

import com.example.local_matching.location.repository.model.LocationData

interface ILocationRetriever {
    fun getLocation() : LocationData
}