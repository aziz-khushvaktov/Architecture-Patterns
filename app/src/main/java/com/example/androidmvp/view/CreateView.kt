package com.example.androidmvp.view

import com.example.androidmvp.model.Post

interface CreateView {
    fun onPostCreateSuccess(post: Post?)
    fun onPostCreateError(error: String)
}