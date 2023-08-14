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
    fun cartDeleteAll(@Path("cartId") cartId: Int): Call<JsonElement>

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

    @POST(API.MYPAGE_ADDRESS_SAVE)
    fun mypageAddressSave(@Body address: JsonObject): Call<JsonElement>

    @GET(API.MYPAGE_DONATION)
    fun mypageDonation(): Call<JsonElement>

    @GET(API.MYPAGE_INFO)
    fun mypageInfo(): Call<JsonElement>

    @GET(API.MYPAGE_ORDER)
    fun mypageOrder(): Call<JsonElement>

    // order controller
    @POST(API.ORDER_CREATE)
    fun orderCreate(@Path("productId") productId: Int): Call<JsonElement>

    @POST(API.ORDER_CREATE_CART)
    fun orderCreateCart(@Path("cartId") cartId: Int): Call<JsonElement>

    @DELETE(API.ORDER_DELETE)
    fun orderDelete(@Path("orderId") orderId: Int): Call<JsonElement>

    @GET(API.ORDER_FINDALL)
    fun orderFindAll(): Call<JsonElement>

    // product controller
    @GET(API.PRODUCT_CATEGORY)
    fun productCategory(@Path("category") category: String): Call<JsonElement>

    @GET(API.PRODUCT_ID)
    fun productId(@Path("id") id: Int): Call<JsonElement>

    @DELETE(API.PRODUCT_DELETE)
    fun productDelete(@Path("id") id: Int): Call<JsonElement>

    @PUT(API.PRODUCT_EDIT)
    fun productEdit(@Path("id") id: Int, @Body product: JsonObject): Call<JsonElement>

    @POST(API.PRODUCT_CREATE_FILE)
    fun productCreateFile(@Body product: JsonObject): Call<JsonElement>

    @GET(API.PRODUCT_LIST)
    fun productList(): Call<JsonElement>

    @GET(API.PRODUCT_LIST_PAGE)
    fun productListPage(@Path("page") page: Int): Call<JsonElement>

    @GET(API.PRODUCT_SEARCH)
    fun productSearch(@Path("keyword") keyword: String): Call<JsonElement>

    // progress controller
    @GET(API.PROGRESS_FINDALL)
    fun progressList(): Call<JsonElement>

    // receipt controller
    @POST(API.RECEIPT_CREATE)
    fun receiptCreate(@Body receipt: JsonObject): Call<JsonElement>

    @DELETE(API.RECEIPT_CANCEL)
    fun receiptCancel(@Path("receiptId") receiptId: Int): Call<JsonElement>

    // user controller
    @GET(API.USER_INFO)
    fun userInfo(): Call<JsonElement>



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


    // 모르겠음. put 공부 후 하기
    @PUT(API.USER_UPDATE)
    fun userUpdate(): Call<JsonElement>
}