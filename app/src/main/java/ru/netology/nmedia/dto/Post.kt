package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val postName: String,
    val postData: String,
    val postText: String,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    var shares: Int = 0,
    val video: String? = null
)

class EditPostResult(
    val newContent: String?,
    val newVideo: String?
)