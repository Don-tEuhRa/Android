package com.dongminpark.reborn.Utils

object Constants {
    const val TAG: String = "로그"
}
enum class RESPONSE_STATE {
    OKAY,
    FAIL
}

object USER{
    // 임시로 여기 적어둠. 나중에 보안을 위해서 local로 옮기거나 바꿔야함
    var ACCESS_TOKEN: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsInJvbGUiOiJVU0VSIiwicHJvdmlkZXIiOiJHT09HTEUiLCJuYW1lIjoiWzMgOiDrsJXrj5nrr7xdIiwidXNlcklkIjoiMyIsImVtYWlsIjoicGRtMDAxMTI1QGdtYWlsLmNvbSIsImlhdCI6MTY5MTkzOTk3NCwiZXhwIjoxNjkyMDI2Mzc0fQ.TqzLoCgJ9saXp1Y7onuUVxCWUfdVe5MPNpOhtUIpxps"
    var USERID: Int = 1
    // 프사 추가
    var NAME: String = ""
    var NAMETEMP = ""
    var DESCRIPTION : String = ""
    var DESCRIPTION_TEMP = ""
    var PROFILEIMGURL = ""
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
    const val MYPAGE_ADDRESS_SAVE = "/mypage/address/save"
    const val MYPAGE_DONATION = "/mypage/donation"
    const val MYPAGE_INFO = "/mypage/info"
    const val MYPAGE_ORDER = "/mypage/order"

    // order controller
    const val ORDER_CREATE = "/order/create/{productId}"
    const val ORDER_CREATE_CART = "/order/create/cart/{cartId}"
    const val ORDER_DELETE = "/order/delete/{orderId}"
    const val ORDER_FINDALL = "/order/findAll"

    // product controller
    const val PRODUCT_CATEGORY = "/product/{category}"
    const val PRODUCT_ID = "/product/{id}"
    const val PRODUCT_DELETE = "/product/{id}/delete"
    const val PRODUCT_EDIT = "/product/{id}/edit"
    const val PRODUCT_CREATE_FILE = "/product/create/file"
    const val PRODUCT_LIST = "/product/list"
    const val PRODUCT_LIST_PAGE = "/product/list/{page}"
    const val PRODUCT_SEARCH = "/product/search/{keyword}"

    // progress controller
    const val PROGRESS_FINDALL = "/progress/findAll"

    // receipt controller
    const val RECEIPT_CREATE = "/receipt/create"
    const val RECEIPT_CANCEL = "/receipt/cancel/{receiptId}"

    // user controller
    const val FIREBASE_CONNECT = "/user/firebase/{uid}"
    const val USER_INFO = "/user/info"



    // 과거 작성 파일 -> 추후 삭제 예정

    // Board
    const val BOARD_CREATE = "/board/create"
    const val BOARD_DELETE_BOARDID = "/board/delete/{boardId}"
    const val BOARD_LIST = "/board/list"
    const val BOARD_LIST_BOARDID = "/board/list/{boardId}"
    const val BOARD_UPDATE_BOARDID = "/board/update/{boardId}"
    const val BOARD_USER_USERID = "/board/user/{userId}"
    const val BOARD_USER_MYPAGE = "/board/user/mypage"
    const val BOARD_SEARCH = "/board/search"

    // FoodBank
    const val FOODBANK_LIST = "/foodbank/list"

    // Likes
    const val LIKE_BOARDID = "/like/{boardId}" // 취소랑 좋아요랑 양식이 같음. 호출할때 다르게?
    const val LIKE_LIST_USERID = "/like/list/{userId}"

    // user-controller
    const val USER_USERID = "/user/{userId}"
    const val USER_DELETE_USERID = "/user/delete/{userId}"

    const val USER_UPDATE = "/user/update"
}

object MESSAGE{
    const val ERROR = "오류가 발생했습니다. 다시 시도해주세요"
    const val SAVE = "저장이 완료되었습니다!"
    const val DELETE = "삭제가 완료되었습니다!"
}