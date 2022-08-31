package ru.netology.nmedia.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.*
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.shareEvent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.videoPostEvent.observe(this) { postVideoContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(postVideoContent)
                putExtra(Intent.ACTION_VIEW, data)
            }
            val videoIntent =
                Intent.createChooser(intent, getString(R.string.chooser_open_video))
            startActivity(videoIntent)
        }

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                binding.group.visibility = View.VISIBLE
                binding.cancelSaveButton.setOnClickListener {
                    binding.cancelGroup.visibility = View.GONE
                    binding.group.visibility = View.GONE
                    hideKeyboard()
                }
                binding.cancelGroup.visibility = View.GONE
                    if (content != null) {
                        binding.cancelGroup.visibility = View.VISIBLE
                        requestFocus()
                        showKeyboard()

                    } else {
                        clearFocus()
                        hideKeyboard()
                    }

                }
            }
        }
    }
