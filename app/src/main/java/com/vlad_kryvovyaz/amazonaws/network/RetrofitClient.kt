package com.vlad_kryvovyaz.amazonaws.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    var instance: Retrofit? = null
        /// any of this  will work
        get() {
            if (field == null) {
                field = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory
                            .create()
                    ).build()
            }
            return field
        }
}