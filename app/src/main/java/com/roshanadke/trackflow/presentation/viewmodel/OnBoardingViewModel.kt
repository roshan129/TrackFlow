package com.roshanadke.trackflow.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.roshanadke.trackflow.data.local.TrackFlowPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

) : ViewModel() {

    private var _age = mutableStateOf("")
    val age: State<String> = _age

    private var _gender = mutableStateOf("")
    val gender: State<String> = _gender

    private var _weight = mutableStateOf("")
    val weight: State<String> = _weight

    private var _height = mutableStateOf("")
    val height: State<String> = _height

    fun setAge(age: String) {
        _age.value = age
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setWeight(weight: String) {
        _weight.value = weight
    }

    fun setHeight(height: String) {
        _height.value = height
    }

    fun saveOnBoardingData(
        gender: String, age: String, weight: String, height: String
    ) {
        TrackFlowPreferences.save(TrackFlowPreferences.KEY_GENDER, gender)
        TrackFlowPreferences.save(TrackFlowPreferences.KEY_AGE, age)
        TrackFlowPreferences.save(TrackFlowPreferences.KEY_WEIGHT, weight)
        TrackFlowPreferences.save(TrackFlowPreferences.KEY_HEIGHT, height)
    }

}