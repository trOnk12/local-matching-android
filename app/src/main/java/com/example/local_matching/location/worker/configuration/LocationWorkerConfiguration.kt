package com.example.local_matching.location.worker.configuration

import com.example.local_matching.location.worker.MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES

class LocationWorkerConfiguration private constructor(
    val requestInterval: RequestInterval = RequestInterval()
) {
    class Builder {

        private var requestInterval: RequestInterval? = null
            set(value) {
                if (value != null) {
                    if (value.minutes < MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES) {
                        throw Exception("Minimum periodic time interval must be greater than 15 minutes")
                    }
                }

                field = value
            }

        fun setRequestInterval(requestInterval: RequestInterval): Builder {
            this.requestInterval = requestInterval

            return this
        }

        fun build(): LocationWorkerConfiguration {
            return LocationWorkerConfiguration(
                requestInterval = requestInterval
                    ?: RequestInterval(MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES)
            )
        }

    }
}

data class RequestInterval(
    val minutes: Long = MINIMUM_PERIODIC_TIME_INTERVAL_IN_MINUTES
)

