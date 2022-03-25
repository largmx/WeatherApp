package com.test.myapplication

import com.test.myapplication.model.WeatherResponse

interface ServiceListener {

    fun onFinished(successful: Boolean, weatherResponse: WeatherResponse?)
}