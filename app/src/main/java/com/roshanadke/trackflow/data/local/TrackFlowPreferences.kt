package com.roshanadke.trackflow.data.local

import android.content.Context
import android.content.SharedPreferences

object TrackFlowPreferences {

    private const val PREF_NAME = "TrackFlowPreferences"

    val KEY_GENDER = "gender"
    val KEY_AGE = "age"
    val KEY_WEIGHT = "weight"
    val KEY_HEIGHT = "height"

    @Volatile
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun save(key: String, value: String) {
        sharedPreferences.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: ""
    }

}