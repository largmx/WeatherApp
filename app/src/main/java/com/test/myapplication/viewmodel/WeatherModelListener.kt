package com.test.myapplication.viewmodel

import com.test.myapplication.model.WeatherResponse

interface WeatherModelListener {
    fun onStarted()
    fun onSuccess(weatherResponse: WeatherResponse?)
    fun onError(message: String )
}