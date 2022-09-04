package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {

    fun likes(postId: Long)

    fun shareCounter(postId: Long)

    fun removeById(postId: Long)

    fun save(post: Post)

    fun getAll(): LiveData<List<Post>>

    companion object {
        const val NEW_POST_ID = 0L
    }
}