package com.example.androidmvp.view

import com.example.androidmvp.model.Post

interface UpdateView {
    fun onPostUpdateSuccess(post: Post?)
    fun onPostUpdateError(error: String)
}