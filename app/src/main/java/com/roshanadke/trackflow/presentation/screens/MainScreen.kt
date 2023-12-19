package com.roshanadke.trackflow.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.roshanadke.trackflow.common.Screen
import com.roshanadke.trackflow.data.local.TrackFlowPreferences
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_AGE
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_GENDER
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_HEIGHT
import com.roshanadke.trackflow.data.local.TrackFlowPreferences.KEY_WEIGHT
import com.roshanadke.trackflow.presentation.ui.theme.LightRed
import com.roshanadke.trackflow.presentation.ui.theme.OnPrimary
import com.roshanadke.trackflow.presentation.ui.theme.TrackFlowTheme
import com.roshanadke.trackflow.presentation.ui.theme.VeryLightRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {

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
                              navController.navigate(Screen.TrackScreen.route)
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
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    TrackFlowTheme {
        MainScreen(navController = rememberNavController())
    }
}