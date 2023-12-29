package com.roshanadke.trackflow.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(interval: Long): Flow<Location>

    fun stopLocationUpdates()

    class LocationException(message: String) : Exception()

}
