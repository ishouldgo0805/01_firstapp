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
                published = "06.08.2022"
            )
        }
    )

    private val posts = checkNotNull(data.value) {
        "Data should be not null"
    }

    override fun likes(postId: Long){
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(likedByMe = !it.likedByMe)
        }
//        return if (data.value.filter { it.id == postId }) checkForK(++(likedPost.likes)) else checkForK(--(likedPost.likes))
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

//    override fun shareCounter() = checkForK(++(data.value!!.shares))
}