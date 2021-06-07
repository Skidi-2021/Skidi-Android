package com.skidi.skidi.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendRetrofit {
    private const val API_URL = "https://api.skidi.learnwithghaniy.web.id"

    val reftrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = reftrofit.create(BackendInterface::class.java)
}