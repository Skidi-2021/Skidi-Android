package com.skidi.skidi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skidi.skidi.model.BackendResponse
import com.skidi.skidi.model.BackendRetrofit
import com.skidi.skidi.model.ChatEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GetResponseViewModel : ViewModel() {
    val chatEntity = MutableLiveData<ChatEntity>()

    fun postSymptom(symptom_name: String, latitude: Double, longitude: Double) {
        BackendRetrofit.instance
            .apiGetInformation(symptom_name, latitude, longitude)
            .enqueue(object : Callback<BackendResponse> {
                override fun onResponse(
                    call: Call<BackendResponse>,
                    response: Response<BackendResponse>
                ) {
                    if (response.isSuccessful) {
                        val chat = ChatEntity(
                            id = 0,
                            sender = "bot",
                            message = "Hai. From image that you send, you got " + response.body()?.data?.attributes?.symptomName + ". \nHere's some first treatment that you can try: \n" + response.body()?.data?.attributes?.sources?.get(
                                0
                            )?.title + "\n" + response.body()?.data?.attributes?.sources?.get(0)?.url,
                            type = "chat",
                            time = Calendar.getInstance().time.toString(),
                            img = null
                        )
                        Log.d("symptom", "POST DATA SUCCESS")
                    }
                }

                override fun onFailure(call: Call<BackendResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getResponse(): MutableLiveData<ChatEntity> = chatEntity

    fun getChat(data: BackendResponse) {
        val response = ChatEntity(
            id = 0,
            sender = "bot",
            message = "Hai. From image that you send, you got " + data.data?.attributes?.symptomName + ". \nHere's some first treatment that you can try: \n" + data.data?.attributes?.sources?.get(
                0
            )?.title + "\n" + data.data?.attributes?.sources?.get(0)?.url,
            type = "chat",
            time = Calendar.getInstance().time.toString(),
            img = null
        )
    }
}