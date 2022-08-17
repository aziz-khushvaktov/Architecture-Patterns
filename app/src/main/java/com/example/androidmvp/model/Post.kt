package com.example.androidmvp.model

import java.io.Serializable

data class Post(
    var id: Int,
    var title: String = "",
    var body: String = ""
): Serializable {
    override fun toString(): String {
        return "Post { " + "id = " + id + ", title = " + title + ", body = " + body
    }
}
