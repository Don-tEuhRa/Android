package com.dongminpark.reborn.Utils

object Constants {
    const val TAG: String = "로그"
}
enum class RESPONSE_STATE {
    OKAY,
    FAIL
}

object API{
    const val BASE_URL = "http://10.0.2.2:8080"

    // cart controller
    const val CART_CREATE = "/cart/create/{productId}"
    const val CART_DELETE = "/cart/delete/{cartId}"
    const val CART_DELETE_ALL = "/cart/deleteAll/{cartId}"
    const val CART_FINDALL = "/cart/findAll"

    // interest controller
    const val INTEREST_DELETE = "/interest/delete/{productId}"
    const val INTEREST_LIST = "/interest/list"
    const val INTEREST_SAVE = "/interest/save/{productId}"

    // mypage controller
    const val MYPAGE = "/mypage/"
    const val MYPAGE_USER_UPDATE = "/mypage/user/update"
    const val MYPAGE_DONATION = "/mypage/donation"
    const val MYPAGE_ORDER = "/mypage/order"

    // order controller
    const val ORDER_SAVE = "/order/save"

    // product controller
    const val PRODUCT_CATEGORY = "/product/category/{category}"
    const val PRODUCT_LIST_UNSOLD = "/product/list/UnSold"
    const val PRODUCT_SEARCH = "/product/search/{keyword}"
    const val PRODUCT_SHOW_ID = "/product/show/{id}"

    // progress controller
    const val PROGRESS_FINDALL = "/progress/findAll"

    // receipt controller
    const val RECEIPT_CREATE = "/receipt/create"

    // user controller
    const val FIREBASE_CONNECT = "/user/firebase/{uid}"
    const val USER_INFO = "/user/info"
}

object MESSAGE{
    const val ERROR = "오류가 발생했습니다. 다시 시도해주세요"
}