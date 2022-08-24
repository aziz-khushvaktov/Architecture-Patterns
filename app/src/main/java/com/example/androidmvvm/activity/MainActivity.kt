package com.example.androidmvvm.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvm.adapter.PostAdapter
import com.example.androidmvvm.databinding.ActivityMainBinding
import com.example.androidmvvm.helper.SwipeToDeleteCallback
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.viewMode.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(): BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val viewModel: MainViewModel by viewModels()
    lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        viewModel.apiGetPostsList()
        showLoading(this)
        viewModel.allPosts.observe(this, {
            refreshAdapter(it)
        })
        enableSwipeToUpdateAndUndo()

        binding.fbAdd.setOnClickListener {
            callCreateActivity()
        }
    }

    fun enableSwipeToUpdateAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val position: Int = viewHolder.adapterPosition
                val item = postAdapter.getData()[position]
                callUpdateActivity(item)
                postAdapter.notifyItemInserted(position)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMain)
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        hideLoading()
        postAdapter = PostAdapter(this, items)
        binding.rvMain.adapter = postAdapter
    }

}