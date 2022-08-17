package com.example.androidmvp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidmvp.databinding.ActivityCreateBinding
import com.example.androidmvp.model.Post
import com.example.androidmvp.presenter.create.CreatePresenter
import com.example.androidmvp.utils.Utils.toast
import com.example.androidmvp.view.CreateView

class CreateActivity : AppCompatActivity(), CreateView {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }
    lateinit var createPresenter: CreatePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        createPresenter = CreatePresenter(this)
        binding.apply {
            bSave.setOnClickListener {
                val id = (System.currentTimeMillis() / 2).toInt()
                val title = etTitle.text.toString()
                val body = etBody.text.toString()
                val post = Post(id, title, body)
                createPresenter.apiPostCreate(post)
                finish()
            }
        }
    }

    override fun onPostCreateSuccess(post: Post?) {
        toast("Post has been saved successfully! title: ${post!!.title}")
    }

    override fun onPostCreateError(error: String) {
        toast("Saving post failed!")
    }

}