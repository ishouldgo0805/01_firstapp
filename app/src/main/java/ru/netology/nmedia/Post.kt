package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val shares: Int = 0,
    val likedByMe: Boolean = false,
    val video: String? = "https://www.youtube.com/watch?v=iO8FMBWKO3Y"

)
