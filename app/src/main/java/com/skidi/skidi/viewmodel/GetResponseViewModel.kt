package com.skidi.skidi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skidi.skidi.model.BackendResponse
import com.skidi.skidi.model.BackendRetrofit
import com.skidi.skidi.model.ChatEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class GetResponseViewModel : ViewModel() {
    private val _chatEntity = MutableLiveData<List<ChatEntity>>()
    val chatEntity: LiveData<List<ChatEntity>> = _chatEntity


    fun postSymptom(symptom_name: String, latitude: Double, longitude: Double) {
        BackendRetrofit.instance
            .apiGetInformation(symptom_name, latitude, longitude)
            .enqueue(object : Callback<BackendResponse> {
                override fun onResponse(
                    call: Call<BackendResponse>,
                    response: Response<BackendResponse>
                ) {
                    if (response.isSuccessful) {
                        val chatList = ArrayList<ChatEntity>()
                        chatList.add(
                            ChatEntity(
                                id = 0,
                                sender = "bot",
                                message = """
                                Hai. From image that you send, you got ${response.body()?.data?.attributes?.symptomName}
                            """.trimIndent(),
                                type = "chat",
                                time = Calendar.getInstance().time.toString(),
                                img = null,
                                link = null
                            )
                        )
                        chatList.add(
                            ChatEntity(
                                id = 0,
                                sender = "bot",
                                message = """
                                Here's some first treatment that you can try:
                            """.trimIndent(),
                                type = "chat",
                                time = Calendar.getInstance().time.toString(),
                                img = null,
                                link = null
                            )
                        )

                        chatList.add(
                            ChatEntity(
                                id = 0,
                                sender = "bot",
                                message = """
                                ${response.body()?.data?.attributes?.sources?.get(0)?.title}
                            """.trimIndent(),
                                type = "chat",
                                time = Calendar.getInstance().time.toString(),
                                img = null,
                                link = response.body()?.data?.attributes?.sources?.get(0)?.url
                            )
                        )

                        chatList.add(
                            ChatEntity(
                                id = 0,
                                sender = "bot",
                                message = """
                                ${response.body()?.data?.attributes?.sources?.get(1)?.title}
                            """.trimIndent(),
                                type = "chat",
                                time = Calendar.getInstance().time.toString(),
                                img = null,
                                link = response.body()?.data?.attributes?.sources?.get(1)?.url
                            )
                        )

                        chatList.add(
                            ChatEntity(
                                id = 0,
                                sender = "bot",
                                message = """
                                According to your location, here's the hospital that have skin specialist that can help you.

                                ${response.body()?.included?.get(0)?.attributes?.get(0)?.name}
                            """.trimIndent(),
                                type = "chat",
                                time = Calendar.getInstance().time.toString(),
                                img = null,
                                link = response.body()?.included?.get(0)?.attributes?.get(0)?.mapsUrl
                            )
                        )

                        _chatEntity.value = chatList
                    }
                }

                override fun onFailure(call: Call<BackendResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }
}