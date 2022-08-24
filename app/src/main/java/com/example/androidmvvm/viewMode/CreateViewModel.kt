package com.example.androidmvvm.viewMode

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.activity.CreateActivity
import com.example.androidmvvm.model.Post
import com.example.androidmvvm.network.RetrofitHttp
import com.example.androidmvvm.network.service.PostService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(val postService: PostService): ViewModel() {

    var newPost = MutableLiveData<Post>()

    fun createNewPost(activity: CreateActivity,post: Post): LiveData<Post> {
        postService.createPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                newPost.value = response.body()
                Toast.makeText(activity, "Post has been created successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                newPost.value = null!!
                Toast.makeText(activity, "Creating post failed!", Toast.LENGTH_SHORT).show()
            }

        })
        return newPost
    }
}