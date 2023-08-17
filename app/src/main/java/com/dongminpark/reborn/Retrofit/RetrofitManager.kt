package com.dongminpark.reborn.Retrofit

import com.dongminpark.reborn.App
import com.dongminpark.reborn.Model.*
import com.dongminpark.reborn.Utils.API
import com.dongminpark.reborn.Utils.RESPONSE_STATE
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // firebase와 DB 연결
    fun firebaseConnect(
        uid: String?,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val term = uid ?: ""

        val call = iRetrofit?.firebaseConnect(uid = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val accessToken = data.get("jwt").asString
                            App.token_prefs.accessToken = accessToken

                            completion(RESPONSE_STATE.OKAY)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }

    // cart controller
    fun cartCreate(productId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.cartCreate(productId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }

    fun cartFinAll(completion: (RESPONSE_STATE, ArrayList<Product>?) -> Unit) {
        val call = iRetrofit?.cartFindAll() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val products = arrayListOf<Product>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val productList = data.getAsJsonArray("cartList")

                            productList.forEach {
                                val item = it.asJsonObject
                                val product = Product(
                                    productId = item.get("productId").asInt,
                                    title = item.get("title").asString,
                                    price = item.get("price").asInt,
                                    // local url을 사용하여 이미지를 불러옴
                                    thumbnailUrl = API.BASE_URL + "/resources/files/" + item.get("thumnailUrl").asString,
                                )
                                products.add(product)
                            }

                            completion(RESPONSE_STATE.OKAY, products)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    fun cartDelete(productId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.cartDeleteAll(productId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }

    // interest controller
    fun interestList(completion: (RESPONSE_STATE, ArrayList<Product>?) -> Unit) {
        val call = iRetrofit?.interestList() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val products = arrayListOf<Product>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val productList = data.getAsJsonArray("productVoList")

                            productList.forEach {
                                val item = it.asJsonObject
                                val product = Product(
                                    productId = item.get("productId").asInt,
                                    title = item.get("title").asString,
                                    price = item.get("price").asInt,
                                    content = item.get("content").asString,
                                    thumbnailUrl = API.BASE_URL + "/resources/files/" + item.get("thumbnailUrl").asString,
                                    categoryName = item.get("categoryName").asString,
                                    imageUrl = item.get("imageUrl").asJsonArray.map { API.BASE_URL + "/resources/files/" + it.asString },
                                    isInterested = item.get("isInterested").asBoolean
                                )
                                products.add(product)
                            }

                            completion(RESPONSE_STATE.OKAY, products)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    fun interestedSave(productId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.interestSave(productId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }

    fun interestedDelete(productId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.interestDelete(productId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }
        })
    }

    // mypage contrller
    fun mypage(completion: (RESPONSE_STATE, MypageUser?) -> Unit) {
        val call = iRetrofit?.mypage() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
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
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    fun mypageDonation(completion: (RESPONSE_STATE, DonationCount?, ArrayList<DonationInfo>?) -> Unit) {
        val call = iRetrofit?.mypageDonation() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val donationCount = data.get("donationCount").asJsonObject
                            val donation = data.getAsJsonArray("donation")

                            val donationCountTemp = DonationCount(
                                receiptCount = donationCount.get("receiptCount").asInt,
                                pickupCount = donationCount.get("pickupCount").asInt,
                                reformCount = donationCount.get("reformCount").asInt,
                                arriveCount = donationCount.get("arriveCount").asInt,
                                productCount = donationCount.get("productCount").asInt,
                                donationCount = donationCount.get("donationCount").asInt
                            )

                            val donationList = arrayListOf<DonationInfo>()
                            donation.forEach {
                                val item = it.asJsonObject
                                val donate = DonationInfo(
                                    receiptStatus = item.get("receiptStatus").asString,
                                    name = item.get("name").asString,
                                    address = item.get("address").asString,
                                    phoneNumber = item.get("phoneNumber").asString,
                                    productId = item.get("productId").asInt,
                                    price = item.get("price").asInt,
                                )

                                donationList.add(donate)
                            }

                            completion(RESPONSE_STATE.OKAY, donationCountTemp, donationList)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null, null)
                    }
                }
            }
        })
    }

    fun mypageOrder(completion: (RESPONSE_STATE, OrderCount?, ArrayList<OrderInfo>?) -> Unit) {
        val call = iRetrofit?.mypageOrder() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val orderCount = data.get("orderCount").asJsonObject
                            val order = data.getAsJsonArray("order")

                            val orderCountTemp = OrderCount(
                                allOrderCount = orderCount.get("allOrderCount").asInt,
                                payCount = orderCount.get("payCount").asInt,
                                deliveryCount = orderCount.get("deliveryCount").asInt,
                                deliveredCount = orderCount.get("deliveredCount").asInt,
                                completeCount = orderCount.get("completeCount").asInt
                            )

                            val orderList = arrayListOf<OrderInfo>()
                            order.forEach {
                                val item = it.asJsonObject
                                val addressTemp = item.get("address").asJsonObject
                                val ord = OrderInfo(
                                    orderId = item.get("orderId").asInt,
                                    status = item.get("status").asString,
                                    phone = item.get("phone").asString,
                                    address = addressTemp.get("address").asString,
                                    addressDetail = addressTemp.get("addressDetail").asString,
                                    zipCode = addressTemp.get("zipCode").asString,
                                    paymentDate = item.get("paymentDate").asString,
                                    thumbnailUrl = item.get("thumbnailUrl").asString,
                                    title = item.get("title").asString,
                                    price = item.get("price").asInt,
                                )

                                orderList.add(ord)
                            }

                            completion(RESPONSE_STATE.OKAY, orderCountTemp, orderList)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null, null)
                    }
                }
            }
        })
    }

    fun mypageUserUpdate(
        name: String,
        address: String,
        addressDetail: String,
        zipCode: Int,
        doorPassword: String,
        phoneNumber: String,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("address", address)
        jsonObject.addProperty("addressDetail", addressDetail)
        jsonObject.addProperty("zipCode", zipCode)
        jsonObject.addProperty("doorPassword", doorPassword)
        jsonObject.addProperty("phoneNumber", phoneNumber)

        val call = iRetrofit?.mypageUserUpdate(jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }


    // order controller
    fun orderCreate(
        usePoint: Int,
        payMethod: String,
        productId: List<Int>,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("usePoint", usePoint)
        jsonObject.addProperty("payMethod", payMethod)
        jsonObject.add("productId", Gson().toJsonTree(productId))

        val call = iRetrofit?.orderCreate(jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }


    // product controller
    fun productCategory(
        category: String,
        completion: (RESPONSE_STATE, ArrayList<Product>?) -> Unit
    ) {
        val call = iRetrofit?.productCategory(category) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val products = arrayListOf<Product>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val productList = data.getAsJsonArray("products")

                            productList.forEach {
                                val item = it.asJsonObject
                                val product = Product(
                                    productId = item.get("productId").asInt,
                                    title = item.get("title").asString,
                                    price = item.get("price").asInt,
                                    content = item.get("content").asString,
                                    thumbnailUrl = API.BASE_URL + "/resources/files/" + item.get("thumbnailUrl").asString,
                                    categoryName = item.get("categoryName").asString,
                                    imageUrl = item.get("imageUrl").asJsonArray.map { API.BASE_URL + "/resources/files/" + it.asString },
                                    isInterested = item.get("isInterested").asBoolean
                                )
                                products.add(product)
                            }

                            completion(RESPONSE_STATE.OKAY, products)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    fun productListUnsold(completion: (RESPONSE_STATE, ArrayList<Product>?) -> Unit) {
        val call = iRetrofit?.productListUnsold() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val products = arrayListOf<Product>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val productList = data.getAsJsonArray("products")

                            productList.forEach {
                                val item = it.asJsonObject
                                val product = Product(
                                    productId = item.get("productId").asInt,
                                    title = item.get("title").asString,
                                    price = item.get("price").asInt,
                                    content = item.get("content").asString,
                                    thumbnailUrl = API.BASE_URL + "/resources/files/" + item.get("thumbnailUrl").asString,
                                    categoryName = item.get("categoryName").asString,
                                    imageUrl = item.get("imageUrl").asJsonArray.map { API.BASE_URL + "/resources/files/" + it.asString },
                                    isInterested = item.get("isInterested").asBoolean
                                )
                                products.add(product)
                            }

                            completion(RESPONSE_STATE.OKAY, products)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    fun productShowID(id: Int, completion: (RESPONSE_STATE, Product?) -> Unit) {
        val call = iRetrofit?.productId(id) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val item = data.get("product").asJsonObject

                            val product = Product(
                                productId = item.get("productId").asInt,
                                title = item.get("title").asString,
                                price = item.get("price").asInt,
                                content = item.get("content").asString,
                                thumbnailUrl = API.BASE_URL + "/resources/files/" + item.get("thumbnailUrl").asString,
                                categoryName = item.get("categoryName").asString,
                                imageUrl = item.get("imageUrl").asJsonArray.map { API.BASE_URL + "/resources/files/" + it.asString },
                                isInterested = item.get("isInterested").asBoolean
                            )

                            completion(RESPONSE_STATE.OKAY, product)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    fun productSearch(keyword: String, completion: (RESPONSE_STATE, ArrayList<Product>?) -> Unit) {
        val call = iRetrofit?.productSearch(keyword) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val products = arrayListOf<Product>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val productList = data.getAsJsonArray("products")

                            productList.forEach {
                                val item = it.asJsonObject
                                val product = Product(
                                    productId = item.get("productId").asInt,
                                    title = item.get("title").asString,
                                    price = item.get("price").asInt,
                                    content = item.get("content").asString,
                                    thumbnailUrl = API.BASE_URL + "/resources/files/" + item.get("thumbnailUrl").asString,
                                    categoryName = item.get("categoryName").asString,
                                    imageUrl = item.get("imageUrl").asJsonArray.map { API.BASE_URL + "/resources/files/" + it.asString },
                                    isInterested = item.get("isInterested").asBoolean
                                )
                                products.add(product)
                            }

                            completion(RESPONSE_STATE.OKAY, products)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }

    // progress controller
    fun progressList(completion: (RESPONSE_STATE, MutableList<ProgressBar>?) -> Unit) {
        val call = iRetrofit?.progressList() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
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
                    else -> {
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
    ) {
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

                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }

    // review controller
    /*
    fun reviewUserId(
        userId: Int,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val call = iRetrofit?.reviewUserId(userId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

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

     */

    fun reviewList(completion: (RESPONSE_STATE, ArrayList<Review>?) -> Unit) {
        val call = iRetrofit?.reviewList() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val reviews = arrayListOf<Review>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val productList = data.getAsJsonArray("reviewList")

                            productList.forEach {
                                val item = it.asJsonObject
                                val review = Review(
                                    userName = item.get("userName").asString,
                                    content = item.get("content").asString,
                                    star = item.get("star").asInt,
                                    createdAt = item.get("createdAt").asString
                                )
                                reviews.add(review)
                            }
                            completion(RESPONSE_STATE.OKAY, reviews)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }
        })
    }

    // review Random
    fun reviewRandom(completion: (RESPONSE_STATE, Review?) -> Unit) {
        val call = iRetrofit?.reviewRandom() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject.get("review").asJsonObject

                            val review = Review(
                                userName = data.get("userName").asString,
                                content = data.get("content").asString,
                                star = data.get("star").asInt,
                                createdAt = data.get("createdAt").asString
                            )

                            completion(RESPONSE_STATE.OKAY, review)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }
        })
    }


    // post controller
    fun postCreate(
        title: String,
        content: String,
        category: String,
        secret: Boolean,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("title", title)
        jsonObject.addProperty("content", content)
        jsonObject.addProperty("category", category)
        jsonObject.addProperty("secrete", secret)

        val call = iRetrofit?.postCreate(jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }

    fun postList(completion: (RESPONSE_STATE, ArrayList<QnAList>?, ArrayList<QnAList>?) -> Unit) {
        val call = iRetrofit?.postList() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val DonatePosts = arrayListOf<QnAList>()
                            val OrderPosts = arrayListOf<QnAList>()
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject
                            val DonatePostList = data.getAsJsonArray("donation")
                            val OrderPostList = data.getAsJsonArray("order")

                            DonatePostList.forEach {
                                val item = it.asJsonObject
                                val post = QnAList(
                                    postid = item.get("postId").asInt,
                                    name = item.get("name").asString,
                                    title = item.get("title").asString,
                                    content = item.get("content").asString,
                                    category = item.get("category").asString,
                                    createdAt = item.get("createdAt").asString,
                                )
                                DonatePosts.add(post)
                            }
                            OrderPostList.forEach {
                                val item = it.asJsonObject
                                val post = QnAList(
                                    postid = item.get("postId").asInt,
                                    name = item.get("name").asString,
                                    title = item.get("title").asString,
                                    content = item.get("content").asString,
                                    category = item.get("category").asString,
                                    createdAt = item.get("createdAt").asString,
                                )
                                OrderPosts.add(post)
                            }

                            completion(RESPONSE_STATE.OKAY, DonatePosts, OrderPosts)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null, null)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null, null)
            }
        })
    }

    fun postReadID(postId: Int, completion: (RESPONSE_STATE, QnAList?, String?, String?) -> Unit) {
        val call = iRetrofit?.postReadId(postId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null, null, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val data = body.get("data").asJsonObject.get("post").asJsonObject
                            val comment = body.get("data").asJsonObject.get("comment").asJsonObject
                            val reviewContent = comment.get("reviewContent").asString
                            val reviewCreateAt = comment.get("reviewDate").asString
                            val info = QnAList(
                                postid = data.get("postId").asInt,
                                name = data.get("name").asString,
                                title = data.get("title").asString,
                                content = data.get("content").asString,
                                category = data.get("category").asString,
                                createdAt = data.get("createdAt").asString,
                                isMe = data.get("me").asBoolean,
                            )
                            completion(RESPONSE_STATE.OKAY, info, reviewContent, reviewCreateAt)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null, null, null)
                    }
                }
            }
        })
    }

    fun postDelete(postId: Int, completion: (RESPONSE_STATE) -> Unit) {
        val call = iRetrofit?.postDelete(postId) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }

    fun postUpdate(
        postId: Int,
        title: String,
        content: String,
        category: String,
        secret: Boolean,
        completion: (RESPONSE_STATE) -> Unit
    ) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("title", title)
        jsonObject.addProperty("content", content)
        jsonObject.addProperty("category", category)
        jsonObject.addProperty("secrete", secret)

        val call = iRetrofit?.postUpdate(postId, jsonObject) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATE.OKAY)
                    }
                    else -> {
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
    fun userInfo(completion: (RESPONSE_STATE, info: User?) -> Unit) {
        val call = iRetrofit?.userInfo() ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATE.FAIL, null)
            }

            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                when (response.code()) {
                    200 -> {
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
                                gatePassword = data.get("gatePassword").asString,
                                zipCode = data.get("zipcode").asInt,
                                phone = data.get("phone").asString,
                                point = data.get("point").asInt
                            )
                            completion(RESPONSE_STATE.OKAY, info)
                        }
                    }
                    else -> {
                        completion(RESPONSE_STATE.FAIL, null)
                    }
                }
            }
        })
    }
}
