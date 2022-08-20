package com.example.androidmvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidmvvm.databinding.ActivityCreateBinding
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.utils.Utils.toast
import com.example.androidmvvm.viewMode.CreateViewModel

class CreateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }
    private val createViewModel by lazy { CreateViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            val id = (System.currentTimeMillis()/2).toInt()
            val post = Post(id,etTitle.text.toString(),etBody.text.toString())
            bUpdate.setOnClickListener {
                createViewModel.createNewPost(this@CreateActivity,post).observe(this@CreateActivity,{
                })
                finish()
            }

        }
    }
}