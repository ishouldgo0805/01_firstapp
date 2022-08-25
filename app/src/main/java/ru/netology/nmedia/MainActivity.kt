package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
