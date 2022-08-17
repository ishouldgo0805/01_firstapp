package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            id = 0,
            author = "Dmitry",
            content = "Events",
            published = "06.08.2022"
        )
    )

    override fun likes(): Int {
        val currentPost = checkNotNull(data.value) {
            "Data should be not null"
        }
        val likedPost = currentPost.copy(likedByMe = !currentPost.likedByMe)
        data.value = likedPost
        return if (data.value?.likedByMe == true) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24
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

    override fun likeCounter() =
        if (data.value?.likedByMe == true) checkForK(++(data.value!!.likes)) else checkForK(--(data.value!!.likes))

    override fun shareCounter() = checkForK(++(data.value!!.shares))
}