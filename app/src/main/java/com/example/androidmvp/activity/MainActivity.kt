package com.example.androidmvp.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvp.adapter.PostAdapter
import com.example.androidmvp.databinding.ActivityMainBinding
import com.example.androidmvp.helper.SwipeToDeleteCallback
import com.example.androidmvp.model.Post
import com.example.androidmvp.presenter.MainPresenter
import com.example.androidmvp.view.MainView

class MainActivity : BaseActivity(), MainView {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var postAdapter: PostAdapter
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        showLoading(this)
        mainPresenter = MainPresenter(this)
        mainPresenter.apiPostList()
        binding.fbAdd.setOnClickListener { callCreateActivity(); postAdapter.notifyDataSetChanged() }
        enableSwipeToDeleteAndUndo()
    }

    fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)

                val position: Int = viewHolder.adapterPosition
                val post = postAdapter.getData()[position]
                callUpdateActivity(post)
                postAdapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMain)
    }

    private fun callUpdateActivity(post: Post) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("updatePost", post)
        startActivity(intent)
    }


    /**
     * Refresh adapter here
     */
    private fun refreshAdapter(items: ArrayList<Post>) {
        hideLoading()
        postAdapter = PostAdapter(this, items)
        binding.rvMain.adapter = postAdapter
    }

    override fun onPostListSuccess(posts: ArrayList<Post>?) {
        refreshAdapter(posts!!)
    }

    override fun onPostListFailure(error: String) {

    }

    override fun onPostDeleteSuccess(post: Post?) {
        mainPresenter.apiPostList()
    }

    override fun onPostDeleteFailure(error: String) {

    }

    private fun callCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }

}