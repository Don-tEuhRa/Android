package com.dongminpark.reborn.Utils.GetAddress

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.dongminpark.reborn.App
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun searchAddress(query: String, postcode: MutableState<String>, home1: MutableState<String>) {
    val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://naveropenapi.apigw.ntruss.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    retrofit.create(NaverMapApi::class.java).searchAddress(query)?.enqueue(object :
        Callback<AddressResponse?> {
        override fun onResponse(call: Call<AddressResponse?>, response: Response<AddressResponse?>) {
            val post = response.body().toString()
            val parts = post.split("\n")
            val address = parts[0]
            val postCode = parts[1]

            home1.value = if (address != "null") address else "" // 주소 텍스트뷰에 도로명주소
            postcode.value = postCode // 우편번호 텍스트뷰에 우편번호
            if (address == "null"){
                Toast.makeText(
                    App.instance,
                    "주소가 올바르지 않습니다",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<AddressResponse?>, t: Throwable) {
            // Handle failure
        }
    })

}