package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.Delegates

class InMemoryPostRepository(
    context: Context
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var nextId = 0L

    override fun getAll(): LiveData<List<Post>> = data




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
        data.value = posts
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
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
        data.value = posts
    }


    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
        data.value = posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
        data.value = posts
    }

    private companion object {
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "posts"

    }
}
