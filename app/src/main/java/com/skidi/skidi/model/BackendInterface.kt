package com.skidi.skidi.model

import retrofit2.Call
import retrofit2.http.*

interface BackendInterface {
    @FormUrlEncoded
    @POST("api/users/1/symptoms")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJnaGFuaXlAa29kZWluLmlkIiwianRpIjoiZWU4NTk0Mjg5MWMxMGFkZDkxOTg0YzgwZjJjMjg4MTkiLCJpYXQiOjE2MjI2MDYxMzIsImV4cCI6MTY1NDE0MjEzMn0.G2GmaWOn8c-T3PUGXQFDCsFOWtz8AbWQKSN6DSThIFA")
    fun apiGetInformation(
        @Field("symptom_name") symptom_name :String,
        @Field("latitude") latitude : Double,
        @Field("longitude") longitude :  Double
    ): Call<BackendResponse>
}