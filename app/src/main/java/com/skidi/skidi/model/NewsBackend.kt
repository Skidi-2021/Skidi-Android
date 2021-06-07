package com.skidi.skidi.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://newsapi.org/v2/"

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface NewsBackend {
    @GET("everything?q=kulit&language=id&apiKey=e61bf8073e6d474fb27c45fc8bcdfccf")
    suspend fun getNews(): NewsResponse
}

object NewsBackendInstance {
    val newsInstance: NewsBackend by lazy {
        retrofit.create(NewsBackend::class.java)
    }
}