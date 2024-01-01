package com.roshanadke.trackflow.presentation.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
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
import com.roshanadke.trackflow.R
import com.roshanadke.trackflow.common.Screen
import com.roshanadke.trackflow.common.hasLocationPermissions
import com.roshanadke.trackflow.common.locationPermissions
import com.roshanadke.trackflow.common.openSettings
import com.roshanadke.trackflow.presentation.components.LocationPermissionTextProvider
import com.roshanadke.trackflow.presentation.components.PermissionDialog
import com.roshanadke.trackflow.presentation.viewmodel.TrackViewModel
import com.roshanadke.trackflow.services.TrackingService
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackScreen(
    navController: NavController,
    viewModel: TrackViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val polylineList by TrackingService.polylineList
    val isTracking by viewModel.isTracking

    val nashik = LatLng(19.949008, 73.768437)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nashik, 5f)
    }

    val mapProperties = MapProperties(isMyLocationEnabled = context.hasLocationPermissions())

    var shouldShowPermissionDialog by remember {
        mutableStateOf(false)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResultMap ->

        val arePermissionsGranted = permissionResultMap.values.reduce { acc, next -> acc && next }

        if (arePermissionsGranted) {
            //do nothing
        } else {
            shouldShowPermissionDialog = true
        }
    }

    LaunchedEffect(Unit) {
        if(!context.hasLocationPermissions()) {
            permissionLauncher.launch(locationPermissions)
        }
    }

    val polylinePoints = listOf(
        LatLng(12.971598, 77.594562),
        LatLng(19.0760, 72.8777),
        LatLng(28.6139, 77.2090)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                })
        }
    ) { paddingValues ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                Modifier.fillMaxSize()
            ) {

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

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {

                Button(onClick = {
                    if(isTracking) {
                        Intent(context, TrackingService::class.java).apply {
                            action = TrackingService.ACTION_STOP
                            context.startService(this)
                        }
                        Toast.makeText(context, "Stopped", Toast.LENGTH_SHORT).show()
                    } else {
                        Intent(context, TrackingService::class.java).apply {
                            action = TrackingService.ACTION_START
                            context.startService(this)
                        }
                        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.setIsTracking(!isTracking)

                }) {
                    if(isTracking) {
                        Text(text = stringResource(id = R.string.stop))
                    } else {
                        Text(text = stringResource(id = R.string.start))
                    }
                }

                if(polylineList.isNotEmpty() && !isTracking) {
                    Button(modifier = Modifier.padding(start = 12.dp),onClick = {
                        /*Intent(context, TrackingService::class.java).apply {
                            action = TrackingService.ACTION_STOP
                            context.startService(this)
                        }*/
                        //Toast.makeText(context, "Finish Run", Toast.LENGTH_SHORT).show()

                        //Save data and show result in next screen.

                    }) {
                        Text(text = "Finish Run")
                    }
                }

            }
        }

        if (shouldShowPermissionDialog) {
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                onDismiss = {
                    shouldShowPermissionDialog = false
                },
                onOkClick = {
                    shouldShowPermissionDialog = false
                    permissionLauncher.launch(locationPermissions)
                },
                onGoToAppSettingsClick = {
                    shouldShowPermissionDialog = false
                    activity.openSettings()
                })
        }

    }


}