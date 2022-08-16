package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    override val data =(
        Post(
            id = 0,
            author = "Dmitry",
            content = "Events",
            published = "06.08.2022"
        )
    )

    override fun likes(): Int {
        data.likedByMe = !data.likedByMe
         return if (data.likedByMe) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24
    }


    override var likes: Int = 0
    override var shares: Int = 0

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
        if (data.likedByMe) checkForK(++likes) else checkForK(--likes)

    override fun shareCounter() = checkForK(++shares)
}