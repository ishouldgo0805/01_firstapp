package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.PostBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0,
            author = "Dmitry",
            content = "Events",
            published = "06.08.2022"
        )

        var likes = 999
        var shares = 1_100_000

        fun checkForK(a: Int): String {
            if (a > 999_999) {
                return ((a / 100000)).toString() + "M"
            } else if (a > 9999) {
                return ((a / 1000)).toString() + "K"
            } else if (a > 999) {
                return "%.1f".format(((a.toFloat() / 1000))) + "K"
            }
            return a.toString()
        }

        binding.likesCounter.text = likes.toString()

        binding.likes.setOnClickListener {
            post.likedByMe = !post.likedByMe
            val imageResId =
                if (post.likedByMe) R.drawable.ic_baseline_favorite_224 else R.drawable.ic_baseline_favorite_24
            binding.likes.setImageResource(imageResId)
            val likeCounter = if (post.likedByMe) checkForK(++likes) else checkForK(--likes)
            binding.likesCounter.text = likeCounter
        }

        binding.share.setOnClickListener {
            binding.shareCounter.text = checkForK(++shares)
        }

    }
}