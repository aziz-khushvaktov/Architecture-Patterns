package com.example.androidmvp.activity

import android.os.Bundle
import com.example.androidmvp.databinding.ActivityUpdateBinding
import com.example.androidmvp.model.Post
import com.example.androidmvp.presenter.update.UpdatePresenter
import com.example.androidmvp.utils.Utils.toast
import com.example.androidmvp.view.UpdateView

class UpdateActivity : BaseActivity(), UpdateView {

    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    lateinit var updatePresenter: UpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        updatePresenter = UpdatePresenter(this)
        val post = intent.getSerializableExtra("updatePost")
        binding.apply {
            post as Post
            val id = post.id
            etTitle.setText(post.title)
            etBody.setText(post.body)

            bUpdate.setOnClickListener {
                val item = Post(id, etTitle.text.toString(), etBody.text.toString())
                updatePost(item)
            }


        }
    }

    private fun updatePost(post: Post) {
        updatePresenter.apiPostUpdate(post)
        finish()
    }

    override fun onPostUpdateSuccess(post: Post?) {
        toast("Post which id: ${post!!.id} has been updated!")
    }

    override fun onPostUpdateError(error: String) {
        toast("Updating post failed!")
    }
}