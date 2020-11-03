package com.example.local_matching.network

import com.example.local_matching.model.Location
import com.example.local_matching.model.User

interface LocalMatchingAPI {

    fun registerUser(user: User)

    fun sendLocation(location: Location)

}