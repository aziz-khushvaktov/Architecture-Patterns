package com.example.androidmvp.presenter.create

import android.widget.Toast
import com.example.androidmvp.model.Post
import com.example.androidmvp.network.RetrofitHttp
import com.example.androidmvp.utils.Utils
import com.example.androidmvp.view.CreateView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePresenter(var createView: CreateView) {
     fun apiPostCreate(post: Post) {
        RetrofitHttp.postService.createPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                createView.onPostCreateSuccess(post)
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                createView.onPostCreateError(t.toString())
            }

        })
    }
}