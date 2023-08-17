package com.dongminpark.reborn.Model

data class QnAList(
    val isReply : Boolean = true,
    val isMe: Boolean = false,
    val name: String = "",
    val content: String ="",
    val category: String = "",
    val postid : Int = 0,
    val title : String = "",
    val isSecret : Boolean = false,
    val createdAt: String = ""
)
