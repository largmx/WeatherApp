package com.test.myapplication

import com.test.myapplication.model.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface WeatherApi {

    @GET("/data/2.5/onecall?")
    fun getWeather(
        @Query(value ="lat") lat: String,
        @Query(value ="lon") lon: String,
        @Query(value ="appid") appid: String,
        @Query(value ="cnt") cnt: Int,
        @Query(value ="exclude") exclude: String,
    ): Call<WeatherResponse>

    companion object {
        operator fun invoke(): WeatherApi {

            val logging = HttpLoggingInterceptor()

            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addNetworkInterceptor(logging)


            val builder = Retrofit.Builder().baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())

                .build()
                .create(WeatherApi::class.java)


            return builder
        }

        fun setBearer(token: String, client: OkHttpClient.Builder) {
            client.addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build()
                chain.proceed(newRequest)
            }.build()
        }
    }


}