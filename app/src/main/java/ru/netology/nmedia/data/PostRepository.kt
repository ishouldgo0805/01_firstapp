package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {

    val data: Post

    fun likes(): Int

    val likes: Int

    val shares: Int

    fun likeCounter(): String

    fun shareCounter(): String

}