package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                author = "Dmitry",
                content = "Events $index",
                published = "06.08.2022",
                likedByMe = false
            )
        }
    )

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override fun likes(postId: Long) {
       posts = posts.map { post ->
           if (post.id == postId) {
               val a = post.copy(likedByMe = !post.likedByMe, likes = if (!post.likedByMe) post.likes + 1 else post.likes - 1)
               a
           } else post
       }
    }

    override fun shareCounter(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                val a = post.copy(shares = (post.shares + 1))
                a
            } else post
        }
    }


}
