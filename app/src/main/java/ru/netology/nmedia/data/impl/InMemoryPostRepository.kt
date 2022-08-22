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
               val a = post.copy(likedByMe = !post.likedByMe)
               if (a.likedByMe) checkForK(++(a.likes)) else checkForK(--(a.likes))
               a
           } else post
       }
    }

    private fun checkForK(a: Int): String {
        if (a > 999_999) {
            return ((a / 100000)).toString() + "M"
        } else if (a > 9999) {
            return ((a / 1000)).toString() + "K"
        } else if (a > 999) {
            return "%.1f".format(((a.toFloat() / 1000))) + "K"
        }
        return a.toString()
    }

    override fun shareCounter(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                val a = post.copy(shares = (checkForK(++(post.shares))).toInt())
                a
            } else post
        }
    }


}
