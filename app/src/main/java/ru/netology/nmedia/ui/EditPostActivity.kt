package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val postContent = intent.getStringExtra(POST_CONTENT_EXTRA_KEY)


        binding.editPost.setText(postContent)

        binding.editPost.requestFocus()


        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.editPost.text?.toString())
        }
    }

    private fun onOkButtonClicked(postContent: String?) {

        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(POST_CONTENT_EXTRA_KEY, postContent)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    companion object {
        const val POST_CONTENT_EXTRA_KEY = "postContent"
    }


    object ResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?): Intent {
            val intent = Intent(context, EditPostActivity::class.java)
            intent.putExtra(POST_CONTENT_EXTRA_KEY, input)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(POST_CONTENT_EXTRA_KEY)
            } else null
    }
}