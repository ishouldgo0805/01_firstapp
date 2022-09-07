package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        val editActivityLauncher = registerForActivityResult(
            EditPostActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onSaveButtonClicked)
        }

        viewModel.editPostEvent.observe(this) {
            editActivityLauncher.launch(it)
        }

        setFragmentResultListener(
            requestKey = NewPostFragment.REQUEST_KEY
        ) { requestKey, bundle ->  
            if (requestKey != NewPostFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(NewPostFragment.REQUEST_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
            parentFragmentManager.commit {
                val fragment = NewPostFragment(initialContent)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)


            binding.saveButton.setOnClickListener {
                with(binding.contentEditText) {
                    val content = text.toString()
                    viewModel.onSaveButtonClicked(content)
                }
            }

            binding.fab.setOnClickListener {
                viewModel.addPostClicked()
            }

            viewModel.currentPost.observe(viewLifecycleOwner) { currentPost ->
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
    }.root

}





