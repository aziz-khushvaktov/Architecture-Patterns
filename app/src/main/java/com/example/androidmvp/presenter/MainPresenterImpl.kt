package com.example.androidmvp.presenter

import com.example.androidmvp.model.Post

interface MainPresenterImpl {

    fun apiPostList()
    fun apiPostDelete(post: Post)
}