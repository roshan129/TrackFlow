package com.roshanadke.trackflow.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrackScreen(
    navController: NavController
) {

    val nashik = LatLng(19.949008, 73.768437)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nashik, 10f)
    }

    Box(
        Modifier.fillMaxSize()
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {

            Text(text = "TrackScreen")

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {

                Marker(
                    state = MarkerState(position = nashik),
                    title = "Nashik",
                    snippet = "Marker in Nashik"
                )

            }



        }

    }


}