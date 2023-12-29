package com.roshanadke.trackflow.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.trackflow.location.LocationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val locationClient: LocationClient
) : ViewModel() {

    private val scope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)

    private var _locationUpdatesState = MutableSharedFlow<Location>()
    val locationUpdatesState: SharedFlow<Location> = _locationUpdatesState

    fun startLocationUpdates() {
        locationClient.getLocationUpdates(2000L)
            .catch { e -> e.printStackTrace() }
            .onEach {location ->
                _locationUpdatesState.emit(location)
            }.launchIn(scope)
    }

    fun stopLocationUpdates() {
        locationClient.stopLocationUpdates()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}