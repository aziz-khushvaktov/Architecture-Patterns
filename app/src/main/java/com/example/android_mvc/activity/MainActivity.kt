package com.example.android_mvc.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvc.adapter.PostAdapter
import com.example.android_mvc.databinding.ActivityMainBinding
import com.example.android_mvc.databinding.ItemPostListBinding
import com.example.android_mvc.helper.SwipeToDeleteCallback
import com.example.android_mvc.helper.SwipeToUpdateCallback
import com.example.android_mvc.model.Post
import com.example.android_mvc.network.RetrofitHttp
import com.example.android_mvc.utils.Utils
import com.example.android_mvc.utils.Utils.toast
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val binding2 by lazy { ItemPostListBinding.inflate(layoutInflater) }
    lateinit var postAdapter: PostAdapter
    lateinit var ll_view: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        ll_view = binding2.llView
        postAdapter = PostAdapter(this, apiGetAllPostsList())
        apiGetAllPostsList()
        enableSwipeToDeleteAndUndo()
        enableSwipeToUpdate()

        binding.fbAdd.setOnClickListener { callCreateActivity() }
    }

    /**
     * Control swipe left and right
     */

    fun enableSwipeToUpdate() {
        val swipeToUpdateCallback = object : SwipeToUpdateCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)

                val position: Int = viewHolder.adapterPosition
                val item = postAdapter.getData()[position]
                openUpdateActivity(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToUpdateCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMain)
    }

    fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)

                val position: Int = viewHolder.adapterPosition
                val item = postAdapter.getData()[position]
                postAdapter.removeItem(position)
                apiPostDelete(item, position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMain)
    }

    /**
     * Functions which works with api
     */

    private fun apiCreateNewPost(post: Post) {
        RetrofitHttp.postService.createPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val title = post.title
                toast("$title: New post has been created successfully!")
                postAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }

    private fun apiPostUpdate(post: Post) {
        RetrofitHttp.postService.updatePost(post.id, post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val title = post.title
                postAdapter.notifyDataSetChanged()
                toast("$title: New post has been created successfully!")
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                toast("Creating post is failed!")
            }

        })
    }

    private fun apiGetAllPostsList(): ArrayList<Post> {
        showLoading(this)
        var list = ArrayList<Post>()
        RetrofitHttp.postService.listPosts().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>,
            ) {
                val items = ArrayList<Post>()
                for (post in response.body()!!) {
                    items.add(post)
                    list.add(post)
                }
                refreshAdapter(items)
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

            }

        })
        return list
    }

    private fun apiPostDelete(post: Post, position: Int) {
        RetrofitHttp.postService.deletePost(post.id).enqueue(object : Callback<Post> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                toast("Post which is id:  ${post.id} has been deleted successfully!")
                postAdapter.notifyDataSetChanged()

                val snackBar = Snackbar.make(ll_view,
                    "Item has been removed from the list!",
                    Snackbar.LENGTH_LONG)
                snackBar.setAction("UNDO", object : View.OnClickListener {

                    override fun onClick(p0: View?) {
                        postAdapter.restoreItem(post, post.id)
                        binding.rvMain.scrollToPosition(position)
                    }
                })
                snackBar.setActionTextColor(Color.YELLOW)
                snackBar.show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }

    /**
     * Launchers here
     */

    val createLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val post = data!!.getSerializableExtra("createPost")
            Log.d("CreatePost", post.toString())
            apiCreateNewPost(post as Post)
        }
    }

    val updateLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val post = data!!.getSerializableExtra("updatedPost")
                apiPostUpdate(post as Post)
            }
        }

    /**
     * Call activities
     */

    private fun callCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        createLauncher.launch(intent)
    }

    private fun openUpdateActivity(post: Post) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("updatingPost", post)
        updateLauncher.launch(intent)
    }

    /**
     * Refresh adapter here
     */

    private fun refreshAdapter(items: ArrayList<Post>) {
        hideLoading()
        postAdapter = PostAdapter(this, items)
        binding.rvMain.adapter = postAdapter
    }

    /**
     * Dialog
     */
    fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete?"

        Utils.customDialog(context, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
            }

            override fun onNegativeClick() {

            }

        })
    }

}