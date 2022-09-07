package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostFragment(
    private val initialContent:String?
) : Fragment() {

    private fun onOkButtonClicked(postContent: String?) {

        if (!postContent.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(REQUEST_KEY, postContent)
            setFragmentResult(REQUEST_KEY,resultBundle)
        }
        parentFragmentManager.popBackStack()
    }

    companion object {
        const val POST_CONTENT_EXTRA_KEY = "postContent"
        const val REQUEST_KEY = "requestKey"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityNewPostBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(initialContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.edit.text?.toString())
        }
    }.root
}