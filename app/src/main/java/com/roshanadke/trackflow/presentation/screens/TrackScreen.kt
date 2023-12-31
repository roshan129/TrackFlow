package com.roshanadke.trackflow.presentation.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.roshanadke.trackflow.presentation.viewmodel.TrackViewModel
import com.roshanadke.trackflow.services.TrackingService
import timber.log.Timber

@Composable
fun TrackScreen(
    navController: NavController,
    viewModel: TrackViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val locationUpdates by viewModel.locationUpdatesState.collectAsState(initial = null)
    val polylineList by viewModel.polylineList


    val nashik = LatLng(19.949008, 73.768437)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nashik, 5f)
    }

    val mapProperties = MapProperties(
        isMyLocationEnabled = true,
    )
    locationUpdates?.let {
        Timber.d("location updates: $it")
    }

    val polylinePoints = listOf(
        LatLng(12.971598, 77.594562), // Bangalore
        LatLng(19.0760, 72.8777),    // Mumbai
        LatLng(28.6139, 77.2090)     // Delhi
    )

    Box(
        Modifier.fillMaxSize()
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {

            Text(text = "TrackScreen")

            Button(onClick = {
                Intent(context, TrackingService::class.java).apply {
                    action = TrackingService.ACTION_START
                    context.startService(this)
                }
                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show()

            }) {
                Text(text = "Start")
            }

            Button(onClick = {
                //viewModel.stopLocationUpdates()
                Intent(context, TrackingService::class.java).apply {
                    action = TrackingService.ACTION_STOP
                    context.startService(this)
                }
                Toast.makeText(context, "Stopped", Toast.LENGTH_SHORT).show()

            }) {
                Text(text = "Stop")
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties
            ) {
                Marker(
                    state = MarkerState(position = nashik),
                    title = "Nashik",
                    snippet = "Marker in Nashik",
                )

                Polyline(points = polylineList, color = Color.Red)
                //Polyline(points = polylinePoints, color = Color.Red)

            }

        }

    }


}