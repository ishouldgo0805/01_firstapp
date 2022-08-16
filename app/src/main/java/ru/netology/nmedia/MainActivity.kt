package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.*
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.likes.setOnClickListener {
            binding.likes.setImageResource(viewModel.onLikeClicked())
            binding.likesCounter.text = viewModel.likeCounter()
        }

        binding.share.setOnClickListener {
            binding.shareCounter.text = viewModel.shareCounter()
        }
    }

}