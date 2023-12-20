package com.roshanadke.trackflow.presentation.screens

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.roshanadke.trackflow.common.Screen
import com.roshanadke.trackflow.common.hasLocationPermissions
import com.roshanadke.trackflow.common.locationPermissions
import com.roshanadke.trackflow.common.openSettings
import com.roshanadke.trackflow.data.local.TrackFlowPreferences
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_AGE
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_GENDER
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_HEIGHT
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_WEIGHT
import com.roshanadke.trackflow.presentation.components.LocationPermissionTextProvider
import com.roshanadke.trackflow.presentation.components.PermissionDialog
import com.roshanadke.trackflow.presentation.ui.theme.OnPrimary
import com.roshanadke.trackflow.presentation.ui.theme.TrackFlowTheme
import com.roshanadke.trackflow.presentation.ui.theme.VeryLightRed

@Composable
fun MainScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    var shouldShowPermissionDialog by remember {
        mutableStateOf(false)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResultMap ->

        val arePermissionsGranted = permissionResultMap.values.reduce { acc, next -> acc && next }

        if (arePermissionsGranted) {
            navController.navigate(Screen.TrackScreen.route)
        } else {
            shouldShowPermissionDialog = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(VeryLightRed)
        ) {

            Column(
                Modifier.fillMaxSize()
            ) {
                Text(text = "MainScreen")
                Text(text = "Age: ${TrackFlowPreferences.getString(KEY_AGE)}")
                Text(text = "height: ${TrackFlowPreferences.getString(KEY_HEIGHT)}")
                Text(text = "weight: ${TrackFlowPreferences.getString(KEY_WEIGHT)}")
                Text(text = "gender: ${TrackFlowPreferences.getString(KEY_GENDER)}")
            }


            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = {
                        if (context.hasLocationPermissions()) {
                            navController.navigate(Screen.TrackScreen.route)
                        } else {
                            permissionLauncher.launch(locationPermissions)
                        }

                    },
                    modifier = Modifier
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnPrimary, contentColor = Color.Black
                    ),

                    ) {
                    Text(
                        text = "Start",
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (shouldShowPermissionDialog) {
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
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

@Preview
@Composable
fun MainScreenPreview() {
    TrackFlowTheme {
        MainScreen(navController = rememberNavController())
    }
}