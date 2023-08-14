package com.dongminpark.reborn.Retrofit

import android.util.Log
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Utils.OAuthData
import com.dongminpark.reborn.Utils.API
import com.dongminpark.reborn.Utils.Constants.TAG
import com.dongminpark.reborn.Utils.RESPONSE_STATE
import com.dongminpark.reborn.Utils.USER
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // firebase와 DB 연결
    fun firebaseConnect(
        uid: String?,
        completion: (RESPONSE_STATE) -> Unit
    ) { // 통신 성공 여부 , 토큰, 멤버 여부
        val term = uid ?: ""

        val call = iRetrofit?.firebaseConnect(uid = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.e(TAG, "FirebaseConnect - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL)
            }

            // 응답 성공시
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.e(TAG,
                    "FirebaseConnect - onResponse() called / respose : ${response.body()}"
                )
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val accessToken = data.get("jwt").asString
                            App.token_prefs.accessToken = accessToken

                            completion(RESPONSE_STATE.OKAY)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }


    // user controller
    fun userInfo(completion: (RESPONSE_STATE, name: String) -> Unit){
        val call = iRetrofit?.userInfo() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "userInfo - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL, "")
            }

            // 응답 성공시
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(
                    TAG,
                    "userInfo - onResponse() called / respose : ${response.body()}"
                )
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject.get("user").asJsonObject
                            val name = data.get("nickname").asString
                            completion(RESPONSE_STATE.OKAY, name)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL, "")
                    }
                }
            }
        })
    }

}