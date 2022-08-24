package com.example.androidmvvm.activity

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.example.androidmvvm.R
import com.example.androidmvvm.adapter.PostAdapter
import com.example.androidmvvm.model.Post
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
open class BaseActivity @Inject constructor(): AppCompatActivity() {
    var progressDialog: AppCompatDialog? = null

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    fun showLoading(activity: Activity?) {
        if (activity == null) return

        if (progressDialog != null && progressDialog!!.isShowing) {

        } else {
            progressDialog = AppCompatDialog(activity, R.style.customDialog)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.custom_progress_dialog)
            val iv_progress = progressDialog!!.findViewById<ImageView>(R.id.iv_progress)
            val animationDrawable = iv_progress!!.drawable as AnimationDrawable
            animationDrawable.start()
            if (!activity.isFinishing) progressDialog!!.show()
        }
    }

    fun hideLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun callUpdateActivity(post: Post) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("updatingPost",post)
        startActivity(intent)
    }

    fun callCreateActivity() {
        val intent = Intent(this,CreateActivity::class.java)
        startActivity(intent)
    }
}