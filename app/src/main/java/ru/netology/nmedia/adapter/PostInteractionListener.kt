package ru.netology.nmedia.adapter

import ru.netology.nmedia.Post

interface PostInteractionListener {

    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditCLicked(post: Post)
    fun onPlayVideoEvent(post: Post)
}