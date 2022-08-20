package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.MinipostBinding
import ru.netology.nmedia.databinding.PostBinding
import kotlin.properties.Delegates

internal class PostsApadter(
    private val onLikeClicked: (Post) -> Unit
) : ListAdapter<Post, PostsApadter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(
        private val binding: MinipostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likes.setOnClickListener { onLikeClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post


            with(binding) {
                authorName.text = post.author
                text.text = post.content
                date.text = post.published
//            likesCounter.text = post.likes.toString()
//            shareCounter.text = post.shares.toString()
                likes.setImageResource(getLikeIconResId(post.likedByMe))
            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24

    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MinipostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}