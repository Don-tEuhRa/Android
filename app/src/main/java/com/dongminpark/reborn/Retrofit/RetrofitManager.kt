package com.dongminpark.reborn.Retrofit

import android.util.Log
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Model.MypageUser
import com.dongminpark.reborn.Model.ProgressBar
import com.dongminpark.reborn.Model.User
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


    // mypage contrller
    fun mypage(completion: (RESPONSE_STATE, MypageUser?) -> Unit){
        val call = iRetrofit?.mypage() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "mypage - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL, null)
            }

            // 응답 성공시
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d( // 지워야함.
                    TAG,
                    "mypage - onResponse() called / respose : ${response.body()}"
                )
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject.get("vo").asJsonObject

                            val info = MypageUser(
                                userId = data.get("userId").asInt,
                                name = data.get("name").asString,
                                point = data.get("point").asInt,
                                donationPoint = data.get("donationPoint").asInt,
                                donationCount = data.get("donationCount").asInt
                            )
                            completion(RESPONSE_STATE.OKAY, info)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }



    // progress controller
    fun progressList(completion: (RESPONSE_STATE, MutableList<ProgressBar>?) -> Unit){
        val call = iRetrofit?.progressList() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "userInfo - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL, null)
            }

            // 응답 성공시
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                Log.d(
                    TAG,
                    "progressBar - onResponse() called / respose : ${response.body()}"
                )
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val progress = mutableListOf<ProgressBar>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val donationStatusList = data.getAsJsonArray("donationStatusList")

                            donationStatusList.forEach {
                                val item = it.asJsonObject
                                val progressBar = ProgressBar(
                                    count = item.get("count").asInt,
                                    donationStatus = item.get("donationStatus").asString
                                )
                                progress.add(progressBar)
                            }

                            completion(RESPONSE_STATE.OKAY, progress)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    // receipt controller
    fun receiptCreate(
        address: String,
        addressDetail: String,
        zipCode: Int,
        date: String,
        gatePassword: String,
        name: String,
        phoneNumber: String,
        completion: (RESPONSE_STATE) -> Unit
    ){
        val jsonObject = JsonObject()
        jsonObject.addProperty("address", address)
        jsonObject.addProperty("addressDetail", addressDetail)
        jsonObject.addProperty("zipCode", zipCode)
        jsonObject.addProperty("date", date)
        jsonObject.addProperty("gatePassword", gatePassword)
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("phoneNumber", phoneNumber)

        val call = iRetrofit?.receiptCreate(jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "gptStop - onResponse() called / respose : ${response.body()}")

                when (response.code()) {
                    200 -> { // 정상 연결
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }

    // user controller
    fun userInfo(completion: (RESPONSE_STATE, info: User?) -> Unit){
        val call = iRetrofit?.userInfo() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "userInfo - onFailure() called / t: $t")
                completion(RESPONSE_STATE.FAIL, null)
            }

            // 응답 성공시
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> { // 정상 연결
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject.get("user").asJsonObject
                            val info = User(
                                userId = data.get("userId").asInt,
                                name = data.get("name").asString,
                                nickname = data.get("nickname").asString,
                                email = data.get("email").asString,
                                address = data.get("address").asString,
                                detailAddress = data.get("detailAddress").asString,
                                zipCode = data.get("zipcode").asInt,
                                phone = data.get("phone").asString,
                                point = data.get("point").asInt
                            )
                            completion(RESPONSE_STATE.OKAY, info)
                        }
                    }
                    else -> { // 에러
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

}