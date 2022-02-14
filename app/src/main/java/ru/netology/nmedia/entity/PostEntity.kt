package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val likedByMe: Boolean,
    val shares: Int,
    val view: Boolean,
    val views: Int,
    val video: String? = null,
) {
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        likes,
        likedByMe,
        shares,
        view,
        views,
        video
    )

    companion object{
        fun fromDto(post: Post) = with(post){
            PostEntity(
                id,
                author,
                content,
                published,
                likes,
                likedByMe,
                shares,
                view,
                views,
                video
            )
        }
    }
}