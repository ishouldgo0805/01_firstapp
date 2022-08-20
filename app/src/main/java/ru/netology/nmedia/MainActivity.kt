package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.impl.PostsApadter
import ru.netology.nmedia.databinding.*
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsApadter(viewModel::onLikeClicked)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

//        binding.share.setOnClickListener {
//            shareCounter.text = viewModel.onShareClicked()
//        }

    }

//    private fun MinipostBinding.render(post: Post) {
//        authorName.text = post.author
//        text.text = post.content
//        date.text = post.published
//        likesCounter.text = post.likes.toString()
//        shareCounter.text = post.shares.toString()
//        likes.setImageResource(getLikeIconResId(post.likedByMe))
//        likes.setOnClickListener { likesCounter.text = viewModel.onLikeClicked(post).toString() }
//    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24

}