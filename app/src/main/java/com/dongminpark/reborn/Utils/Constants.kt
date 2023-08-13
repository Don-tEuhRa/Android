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
    var ACCESS_TOKEN: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsInJvbGUiOiJBRE1JTiIsInByb3ZpZGVyIjoiR09PR0xFIiwibmFtZSI6IlsyIDog67Cc656E7ZWc64u56re8ODIxNV0iLCJ1c2VySWQiOiIyIiwiZW1haWwiOiJ5ZW9uZ2hhazk5MDVAZ21haWwuY29tIiwiaWF0IjoxNjkxOTA4NzExLCJleHAiOjE2OTE5OTUxMTF9.qg6FXeKlLW0OctMrxxv8ddIuBenPLwDRz4z_PvSQV0I"
    var USERID: Int = 1
    // 프사 추가
    var NAME: String = ""
    var NAMETEMP = ""
    var DESCRIPTION : String = ""
    var DESCRIPTION_TEMP = ""
    var PROFILEIMGURL = ""
}

object API{
    const val BASE_URL = "http://118.67.142.179:8080" // 변경 예정

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
    const val FIREBASE_CONNECT = "/user/firebase/{uid}"
    const val USER_UPDATE = "/user/update"
}

object MESSAGE{
    const val ERROR = "오류가 발생했습니다. 다시 시도해주세요"
    const val SAVE = "저장이 완료되었습니다!"
    const val DELETE = "삭제가 완료되었습니다!"
}