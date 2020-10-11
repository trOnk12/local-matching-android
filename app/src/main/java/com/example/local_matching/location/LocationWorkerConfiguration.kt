package com.example.local_matching.location

class LocationWorkerConfiguration(
    val requestInterval: RequestInterval = RequestInterval()
) {
    internal class Builder {

        private var requestInterval: RequestInterval = RequestInterval()

        fun setRequestInterval(requestInterval: RequestInterval): Builder {
            this.requestInterval = requestInterval

            return this
        }

        fun build(): LocationWorkerConfiguration {
            return LocationWorkerConfiguration(requestInterval)
        }

    }
}

data class RequestInterval(
    val minutes: Long = LocationWorkerManager.MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES
)

