package com.dongminpark.reborn.Model

data class QnAList(
    val isReply : Boolean,
    val isMe: Boolean,
    val name: String,
    val postid : Long,
    val title : String,
    val isSecret : Boolean,
    val createdAt: String
)
