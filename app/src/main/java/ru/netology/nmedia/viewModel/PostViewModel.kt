package ru.netology.nmedia.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.databinding.MinipostBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    val shareEvent = SingleLiveEvent<String>()

    val videoPostEvent = SingleLiveEvent<String>()


    fun onSaveButtonClicked(content: String) {

        if (content.isBlank()) return


        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content= content,
            published = "Today",
            video = "https://www.youtube.com/watch?v=iO8FMBWKO3Y"
        )
        repository.save(post)
        currentPost.value = null

    }

    override fun onLikeClicked(post: Post) =
        repository.likes(post.id)

    override fun onShareClicked(post: Post) {
        repository.shareCounter(post.id)
        shareEvent.value = post.content
    }

    override fun onRemoveClicked(post: Post) =
        repository.removeById(post.id)

    override fun onEditCLicked(post: Post) {
        currentPost.value = post
    }

    override fun onPlayVideoEvent(post: Post) {
        videoPostEvent.value = post.video ?: return
    }


}