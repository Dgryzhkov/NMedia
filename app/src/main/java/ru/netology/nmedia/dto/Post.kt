package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var share: Boolean = false,
    var shares: Int = 0,
    var view:Boolean=false,
    var views: Int=0
)