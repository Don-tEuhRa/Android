package com.dongminpark.reborn.Retrofit

import com.dongminpark.reborn.Utils.API
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {
    // firebae
    @POST(API.FIREBASE_CONNECT)
    fun firebaseConnect(@Path("uid") uid: String):Call<JsonElement>

    // cart controller
    @POST(API.CART_CREATE)
    fun cartCreate(@Path("productId") productId: Int): Call<JsonElement>

    @DELETE(API.CART_DELETE)
    fun cartDelete(@Path("cartId") cartId: Int, @Body productIdList: JsonObject): Call<JsonElement>

    @DELETE(API.CART_DELETE_ALL)
    fun cartDeleteAll(@Path("productId") cartId: Int): Call<JsonElement>

    @GET(API.CART_FINDALL) // 500 에러
    fun cartFindAll(): Call<JsonElement>

    // interest controller
    @DELETE(API.INTEREST_DELETE)
    fun interestDelete(@Path("productId") productId: Int): Call<JsonElement>

    @GET(API.INTEREST_LIST)
    fun interestList(): Call<JsonElement>

    @POST(API.INTEREST_SAVE)
    fun interestSave(@Path("productId") productId: Int): Call<JsonElement>

    // mypage controller
    @GET(API.MYPAGE)
    fun mypage(): Call<JsonElement>

    @PUT(API.MYPAGE_USER_UPDATE)
    fun mypageUserUpdate(@Body address: JsonObject): Call<JsonElement>

    @GET(API.MYPAGE_DONATION)
    fun mypageDonation(): Call<JsonElement>

    @GET(API.MYPAGE_ORDER)
    fun mypageOrder(): Call<JsonElement>

    // order controller
    @POST(API.ORDER_SAVE)
    fun orderCreate(@Body product: JsonObject): Call<JsonElement>


    // product controller
    @GET(API.PRODUCT_CATEGORY)
    fun productCategory(@Path("category") category: String): Call<JsonElement>

    @GET(API.PRODUCT_SHOW_ID)
    fun productId(@Path("id") id: Int): Call<JsonElement>

    @GET(API.PRODUCT_LIST_UNSOLD)
    fun productListUnsold(): Call<JsonElement>

    @GET(API.PRODUCT_SEARCH)
    fun productSearch(@Path("keyword") keyword: String): Call<JsonElement>

    // progress controller
    @GET(API.PROGRESS_FINDALL)
    fun progressList(): Call<JsonElement>

    // receipt controller
    @POST(API.RECEIPT_CREATE)
    fun receiptCreate(@Body receipt: JsonObject): Call<JsonElement>

    // post controller
    @POST(API.POST_CREATE)
    fun postCreate(@Body post: JsonObject): Call<JsonElement>

    @GET(API.POST_LIST)
    fun postList(): Call<JsonElement>

    @GET(API.POST_READ_ID)
    fun postReadId(@Path("postId") id: Int): Call<JsonElement>

    @DELETE(API.POST_DELETE)
    fun postDelete(@Path("postId") id: Int): Call<JsonElement>

    @PUT(API.POST_UPDATE)
    fun postUpdate(@Path("postId") id: Int, @Body post: JsonObject): Call<JsonElement>

    // user controller
    @GET(API.USER_INFO)
    fun userInfo(): Call<JsonElement>
}