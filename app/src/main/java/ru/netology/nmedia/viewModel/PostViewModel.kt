package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.dto.EditPostResult
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository =
        FilePostRepository(application)

    val data by repository::data

    private val currentPost = MutableLiveData<Post?>(null)

    val shareEvent = SingleLiveEvent<String>()

    val navigateToPostContentScreenEvent = SingleLiveEvent<EditPostResult?>()

    val navigateToPostFragmentEvent = SingleLiveEvent<Long>()

    val navigateToVideo = SingleLiveEvent<String?>()

    fun onSaveButtonClicked(content: String, video: String?) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            postText = content,
            video = video
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            postText = content,
            postData = "Сегодня",
            postName = "Andrey",
            video = video
        )
        repository.savePost(post)
        currentPost.value = null
    }

    fun addPostClicked() {
        navigateToPostContentScreenEvent.call()
    }

    override fun onLikeClicked(post: Post) =
        repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        shareEvent.value = post.postText
    }

    override fun onDeleteClicked(post: Post) =
        repository.deletePost(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = EditPostResult(post.postText, post.video)
    }

    override fun onVideoClicked(post: Post) {
        navigateToVideo.value = post.video
    }

    override fun onPostClicked(post: Post) {
        navigateToPostFragmentEvent.value = post.id
    }
}