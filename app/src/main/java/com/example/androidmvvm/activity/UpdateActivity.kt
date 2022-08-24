package com.example.androidmvvm.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.androidmvvm.databinding.ActivityUpdateBinding
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.viewMode.UpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class UpdateActivity @Inject constructor(): BaseActivity() {

    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    private val viewModel: UpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        val post = intent.getSerializableExtra("updatingPost")
        post as Post
        binding.apply {
            val id = post.id
            etTitle.setText(post.title)
            etBody.setText(post.body)

            bUpdate.setOnClickListener {
                updatePost(id)
            }
        }
    }

    private fun updatePost(id: Int) {
        val post = Post(id, binding.etTitle.text.toString(), binding.etBody.text.toString())
        viewModel.updatePost(post).observe(this,{
            viewModel.updatedPost
        })
        finish()
    }
}