package com.roshanadke.trackflow.presentation.screens

import android.util.Log
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.roshanadke.trackflow.R
import com.roshanadke.trackflow.common.Screen
import com.roshanadke.trackflow.data.local.TrackFlowPreferences
import com.roshanadke.trackflow.presentation.ui.theme.LightRed
import com.roshanadke.trackflow.presentation.ui.theme.OnPrimary
import com.roshanadke.trackflow.presentation.ui.theme.TrackFlowTheme
import com.roshanadke.trackflow.presentation.viewmodel.OnBoardingViewModel

@Composable
fun OnboardingScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {


    Log.d("TAG", "OnboardingScreen: age1:  ${onBoardingViewModel.age.value} ")
    Box(
        Modifier
            .fillMaxSize()
            .background(LightRed)
    ) {
        val context = LocalContext.current

        val age by onBoardingViewModel.age

        val gender by onBoardingViewModel.gender

        val weight by onBoardingViewModel.weight

        val height by onBoardingViewModel.height

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.body_measurement),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            CustomDropDown(
                dropDownList = stringArrayResource(id = R.array.select_gender).toList(),
                onItemSelected = {
                    onBoardingViewModel.setGender(it)
                },
            )

            Spacer(modifier = Modifier.height(18.dp))

            val ageList = MutableList(61) { (it + 10).toString() }
            ageList.add(0, "Select Age")
            CustomDropDown(
                dropDownList = ageList,
                onItemSelected = {
                    onBoardingViewModel.setAge(it)
                },
            )

            Spacer(modifier = Modifier.height(18.dp))

            val weightList = MutableList(121) { (it + 30).toString() }
            weightList.add(0, "Select Weight")
            CustomDropDown(
                dropDownList = weightList,
                onItemSelected = {
                    onBoardingViewModel.setWeight(it)
                },
            )

            Spacer(modifier = Modifier.height(18.dp))

            val heightList = MutableList(160) { (it + 100).toString() }
            heightList.add(0, "Select Height(cms)")
            CustomDropDown(
                dropDownList = heightList,
                onItemSelected = {
                    onBoardingViewModel.setHeight(it)
                },
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OnPrimary, contentColor = Color.Black
                ),
                onClick = {
                    onBoardingViewModel.saveOnBoardingData(
                        gender,
                        age,
                        weight,
                        height
                    )
                    navController.navigate(Screen.MainScreen.route)
                }) {
                Text(
                    text = "Submit",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown(
    dropDownList: List<String>,
    onItemSelected: (String) -> Unit,
) {
    val context = LocalContext.current
    //val dropDownList = stringArrayResource(id = R.array.select_gender)
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(dropDownList[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)

    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                dropDownList.forEach { item ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun OnboardingScreenPreview() {
    TrackFlowTheme {
        OnboardingScreen(navController = rememberNavController())
    }
}