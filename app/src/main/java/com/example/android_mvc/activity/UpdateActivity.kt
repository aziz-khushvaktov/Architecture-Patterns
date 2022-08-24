package com.example.android_mvc.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_mvc.databinding.ActivityUpdateBinding
import com.example.android_mvc.model.Post
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateActivity @Inject constructor(): BaseActivity() {

    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        val post = intent.getSerializableExtra("updatingPost")
        post as Post
        binding.apply {
            etTitle.setText(post.title)
            etBody.setText(post.body)

            bUpdate.setOnClickListener {
                backToFinish(post.id)
            }
        }

    }

    private fun backToFinish(id: Int) {
        val post = Post(id = id, title = binding.etTitle.text.toString(), body = binding.etBody.text.toString())
        val intent = Intent()
        intent.putExtra("updatedPost",post)
        setResult(RESULT_OK,intent)
        finish()
    }
}