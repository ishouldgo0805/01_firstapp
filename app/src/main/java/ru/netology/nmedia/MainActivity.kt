package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.*
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.likes.setOnClickListener {
            viewModel.onLikeClicked()
        }
        binding.share.setOnClickListener {
            viewModel.onShareClicked()
        }
    }

    private fun PostBinding.render(post: Post) {
        authorName.text = post.author
        text.text = post.content
        date.text = post.published
        likes.setImageResource(getLikeIconResId(post.likedByMe))
        likesCounter.text = post.likes.toString()
        shareCounter.text = post.shares.toString()
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24

}