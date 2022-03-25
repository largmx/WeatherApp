package com.test.myapplication.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.test.myapplication.Constants
import com.test.myapplication.ServiceListener
import com.test.myapplication.model.WeatherResponse
import com.test.myapplication.service.WeatherService

class WeatherViewModel  : ViewModel() , ServiceListener {
    var listener: WeatherModelListener? = null


    fun getWeather( ){
        listener?.onStarted()
        WeatherService().getWeather(this)
    }

    override fun onFinished(successful: Boolean, weatherResponse: WeatherResponse?) {
        if (successful){
            listener?.onSuccess(weatherResponse)
        }else{
            listener?.onError("Ocurrio un error")
        }

        Log.i(Constants.TAG, "successful = "+ successful);
    }


}