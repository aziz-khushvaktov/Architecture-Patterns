package com.example.androidmvp.presenter.update

import com.example.androidmvp.model.Post
import com.example.androidmvp.network.RetrofitHttp
import com.example.androidmvp.view.UpdateView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePresenter(var updateView: UpdateView) {
     fun apiPostUpdate(post: Post) {
        RetrofitHttp.postService.updatePost(post.id,post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                updateView.onPostUpdateSuccess(post)
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                updateView.onPostUpdateError(t.toString())
            }

        })
    }
}