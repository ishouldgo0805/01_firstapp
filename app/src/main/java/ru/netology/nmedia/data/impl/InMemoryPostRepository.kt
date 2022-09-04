package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Dmitry",
                content = "Events $index",
                published = "06.08.2022",
                likedByMe = false,
                video = "https://www.youtube.com/"
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
                val a = post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (!post.likedByMe) post.likes + 1 else post.likes - 1
                )
                a
            } else post
        }
        posts.map { }
    }

    override fun shareCounter(postId: Long) {
        posts = posts.map { post ->
            if (post.id == postId) {
                val a = post.copy(shares = (post.shares + 1))
                a
            } else post
        }
    }

    override fun removeById(postId: Long) {
        posts = posts.filter { post -> post.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }


    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }

}
