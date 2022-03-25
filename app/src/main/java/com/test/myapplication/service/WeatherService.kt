package com.test.myapplication.service


import com.test.myapplication.WeatherApi
import com.test.myapplication.Constants
import com.test.myapplication.ServiceListener
import com.test.myapplication.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherService {

    var listener: ServiceListener? = null


    fun getWeather(authListener: ServiceListener) {
        listener = authListener
        WeatherApi().getWeather(Constants.LAT, Constants.LON, Constants.APP_ID, Constants.CNT, Constants.EXCLUDE)
            .enqueue(object : Callback<WeatherResponse> {

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    listener!!.onFinished(false, null)
                }

                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {

                    if (response.isSuccessful && response.code() == Constants.SUCESSFUL) {
                        listener!!.onFinished(true, response.body())
                    }

                }
            })

    }

}