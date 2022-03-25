package com.test.myapplication.model

import com.google.gson.annotations.SerializedName

class DailyModel constructor(

    @SerializedName("dt")
    var dt: Long,

@SerializedName("pressure")
var pressure: Int,

@SerializedName("humidity")
var humidity: String,

@SerializedName("weather")
var weather: ArrayList<WeatherModel>,

)