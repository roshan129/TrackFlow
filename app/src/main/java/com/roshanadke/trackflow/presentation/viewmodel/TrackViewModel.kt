package com.roshanadke.trackflow.presentation.viewmodel

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.roshanadke.trackflow.location.LocationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val locationClient: LocationClient
) : ViewModel() {

    private val scope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)

    private var _locationUpdatesState = MutableSharedFlow<Location>()
    val locationUpdatesState: SharedFlow<Location> = _locationUpdatesState

    private var _polylineList: MutableState<List<LatLng>> = mutableStateOf(mutableListOf())
    val polylineList: State<List<LatLng>> = _polylineList

    fun startLocationUpdates() {
        locationClient.getLocationUpdates(2000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                _locationUpdatesState.emit(location)
                addLatLngToPolygonList(latLng)
            }.launchIn(scope)
    }

    private fun addLatLngToPolygonList(newLatLng: LatLng) {
        val currentPolygonList = _polylineList.value.toMutableList()
        currentPolygonList.add(newLatLng)
        _polylineList.value = currentPolygonList
    }

    fun stopLocationUpdates() {
        locationClient.stopLocationUpdates()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}