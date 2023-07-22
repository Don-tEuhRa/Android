package com.dongminpark.reborn.Retrofit


import com.dongminpark.reborn.Utils.API
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {
    // Board
    @POST(API.BOARD_CREATE)
    fun boardCreate(@Body BoardRequestDto: JsonObject) : Call<JsonElement>

    @DELETE(API.BOARD_DELETE_BOARDID)
    fun boardDeleteBoardId(@Path("boardId") boardId: Int): Call<JsonElement>

    @GET(API.BOARD_LIST)
    fun boardList(): Call<JsonElement>

    @GET(API.BOARD_LIST_BOARDID)
    fun boardListBoardId(@Path("boardId") boardId: Int): Call<JsonElement>

    @GET(API.BOARD_SEARCH)
    fun boardSearch(@Query("keyword") keyword: String): Call<JsonElement>

    @POST(API.BOARD_UPDATE_BOARDID)
    fun boardUpdateBoardId(@Path("boardId") boardId: Int) : Call<JsonElement>

    @GET(API.BOARD_USER_USERID)
    fun boardUserUserId(@Path("userId") userId: Int): Call<JsonElement>

    @GET(API.BOARD_USER_MYPAGE)
    fun boardUserMypage(): Call<JsonElement>

    // FoodBank
    @POST(API.FOODBANK_LIST)
    fun foodbankList(@Body location: JsonObject) : Call<JsonElement>

    // Like
    @GET(API.LIKE_BOARDID)
    fun likeBoardId(@Path("boardId") boardId: Int) : Call<JsonElement>

    @DELETE(API.LIKE_BOARDID)
    fun likeDeleteBoardId(@Path("boardId") boardId: Int) : Call<JsonElement>

    @GET(API.LIKE_LIST_USERID)
    fun likeListUserId(@Path("userId") userId: Int) : Call<JsonElement>

    // User
    @GET(API.USER_USERID)
    fun userUserId(@Path("userId") userId: Int): Call<JsonElement>

    @DELETE(API.USER_DELETE_USERID)
    fun userDeleteUserId(@Path("userId") userId: Int): Call<JsonElement>

    @POST(API.FIREBASE_CONNECT)
    fun firebaseConnect(@Path("uid") uid: String):Call<JsonElement>

    // 모르겠음. put 공부 후 하기
    @PUT(API.USER_UPDATE)
    fun userUpdate(): Call<JsonElement>
}