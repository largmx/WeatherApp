package com.test.myapplication.model

import com.google.gson.annotations.SerializedName

class WeatherModel constructor(

@SerializedName("id")
var id: Int,

@SerializedName("main")
var main: String,

@SerializedName("description")
var description: String,

)