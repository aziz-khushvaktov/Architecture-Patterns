package com.example.androidmvvm.viewMode

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.activity.MainActivity
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.network.RetrofitHttp
import com.example.androidmvvm.network.service.PostService
import com.example.androidmvvm.utils.Utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var activity: MainActivity, var postService: PostService) : ViewModel() {

    val allPosts = MutableLiveData<ArrayList<Post>>()
    var deletedPost = MutableLiveData<Post>()

    /**
     * Functions which works with api
     */

    fun apiGetPostsList() {
        postService.listPosts().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>,
            ) {
                allPosts.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

            }

        })
    }

    fun apiPostDelete(post: Post) {
        postService.deletePost(post.id).enqueue(object : Callback<Post> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                activity.toast("Post which is id:  ${post.id} has been deleted successfully!")
                deletedPost.value = response.body()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                allPosts.value = null!!
                activity.toast("Deleting post failed!")
            }

        })
    }


}