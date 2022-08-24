package com.example.androidmvvm.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.androidmvvm.databinding.ActivityCreateBinding
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.viewMode.CreateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateActivity @Inject constructor() : BaseActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(LayoutInflater.from(this)) }
    private val createViewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            val id = (System.currentTimeMillis() / 2).toInt()
            val post = Post(id, etTitle.text.toString(), etBody.text.toString())
            bUpdate.setOnClickListener {
                createViewModel.createNewPost(this@CreateActivity, post)
                    .observe(this@CreateActivity, {
                    })
                finish()
            }

        }
    }
}