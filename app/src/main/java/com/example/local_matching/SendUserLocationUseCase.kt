package com.example.local_matching

import com.example.local_matching.repository.LocationRepository
import com.example.local_matching.network.LocalMatchingAPI
import com.example.local_matching.repository.NoUserFoundException
import com.example.local_matching.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class SendUserLocationUseCase
@Inject constructor(
    private val locationRepository: LocationRepository,
    private val userRepository: UserRepository,
) {

    fun execute() {
        val locationData = locationRepository.getLocation()

        if (locationData != null) {
            try {
                val user = userRepository.getLoggedUser()

                locationRepository.sendLocation(user.id, locationData)

            } catch (noUserFoundException: NoUserFoundException) {
                Timber.i("Could not find a user, are you registered ?")
            }
        } else {
            Timber.i("Location could not be found, yet.")
        }
    }

}