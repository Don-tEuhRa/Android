package com.dongminpark.reborn.Utils.GetAddress

import com.dongminpark.reborn.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverMapApi {
    // local.properties에 넣어둔 키값을 가져온다.
    @Headers("X-NCP-APIGW-API-KEY-ID: ${BuildConfig.NAVER_KEY_ID} ", "X-NCP-APIGW-API-KEY: ${BuildConfig.NAVER_KEY} ")
    @GET("/map-geocode/v2/geocode")
    fun searchAddress(@Query("query") query: String?): Call<AddressResponse?>?
}