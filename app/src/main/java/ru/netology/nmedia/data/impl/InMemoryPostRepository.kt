package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var post = (
        Post(
            id = 0,
            author = "Netology",
            content = "Yo1",
            published = "18.08.2022"
        )
    )

    override val data = MutableLiveData(post)

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

    override fun shareCounter() {
        val currentPost = checkNotNull(data.value) {
            "Data should be not null"
        }
        val sharedPost = currentPost.copy(shares = (checkForK(++(data.value!!.shares))).toInt())
        data.value = sharedPost
    }

    override fun likes() {
        val currentPost = checkNotNull(data.value) {
            "Data should be not null"
        }
        val likedPost = currentPost.copy(likedByMe = !currentPost.likedByMe)
        if (likedPost.likedByMe) checkForK(++(likedPost.likes)) else checkForK(--(likedPost.likes))
        data.value = likedPost
    }

}