package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val likedByMe: Boolean,
    val share: Boolean,
    val shares: Int,
    val view:Boolean,
    val views: Int,
    val video: String?=null
)