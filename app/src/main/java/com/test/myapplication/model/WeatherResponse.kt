package com.test.myapplication.model

import com.google.gson.annotations.SerializedName

class WeatherResponse constructor(

    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lon")
    var lon: Double,

    @SerializedName("daily")
    var dailyModel: ArrayList<DailyModel>,

    ){
    constructor() :this(0.0,0.0,ArrayList(7))
}