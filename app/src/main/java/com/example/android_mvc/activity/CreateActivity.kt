package com.example.android_mvc.activity

import android.content.Intent
import android.os.Bundle
import com.example.android_mvc.databinding.ActivityCreateBinding
import com.example.android_mvc.model.Post
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateActivity @Inject constructor() : BaseActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            bSave.setOnClickListener { backToFinish() }
        }
    }

    private fun backToFinish() {
        val id = (System.currentTimeMillis() / 2).toInt()
        val post = Post(id, binding.etTitle.text.toString(), binding.etBody.text.toString())

        val intent = Intent()
        intent.putExtra("createPost", post)
        setResult(RESULT_OK, intent)
        finish()
    }
}