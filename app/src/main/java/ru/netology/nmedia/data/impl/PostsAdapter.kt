package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.MinipostBinding


typealias OnLikeListener = (Post) -> Unit

typealias OnShareListener = (Post) -> Unit

internal class PostsAdapter(
    private val onLikeClicked: OnLikeListener,
    private val onShareClicked: OnShareListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MinipostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    inner class ViewHolder(
        private val binding: MinipostBinding,
        private val onLikeClicked: OnLikeListener,
        private val onShareClicked: OnShareListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likes.setOnClickListener {
                onLikeClicked(post)

            }
            binding.share.setOnClickListener {
                onShareClicked(post)
            }
        }


        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                text.text = post.content
                date.text = post.published
                likesCounter.text = checkForK(post.likes)
                shareCounter.text = checkForK(post.shares)
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24
                )
            }
        }
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

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }

}
