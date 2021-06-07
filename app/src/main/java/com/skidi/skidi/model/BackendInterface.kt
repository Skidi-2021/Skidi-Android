package com.skidi.skidi.model

import retrofit2.Call
import retrofit2.http.*

interface BackendInterface {
    @FormUrlEncoded
    @POST("api/users/1/symptoms")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJnaGFuaXlAa29kZWluLmlkIiwianRpIjoiM2ZiODE1MDVmY2E1N2EyNGI4NjMyYjZiNGZhMmM5Y2QiLCJpYXQiOjE2MjI5NjQ4MDQsImV4cCI6MTY1NDUwMDgwNH0.jSNGusvlRwo3AL8SLWpKwQCaPz5vUo70eElVHe0zSLw")
    fun apiGetInformation(
        @Field("symptom_name") symptom_name :String,
        @Field("latitude") latitude : Double,
        @Field("longitude") longitude :  Double
    ): Call<BackendResponse>
}