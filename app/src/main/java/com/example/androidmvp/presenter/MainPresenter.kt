package com.example.androidmvp.presenter

import com.example.androidmvp.model.Post
import com.example.androidmvp.network.RetrofitHttp
import com.example.androidmvp.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var mainView: MainView) : MainPresenterImpl {
    override fun apiPostList() {
        RetrofitHttp.postService.listPosts().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>,
            ) {

                mainView.onPostListSuccess(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                mainView.onPostListFailure(t.toString())
            }

        })
    }

    override fun apiPostDelete(post: Post) {
        RetrofitHttp.postService.deletePost(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                mainView.onPostDeleteSuccess(post)

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                mainView.onPostDeleteFailure(t.toString())
            }

        })
    }


}