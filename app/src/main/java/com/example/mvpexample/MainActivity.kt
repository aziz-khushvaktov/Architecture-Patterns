package com.example.mvpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.mvpexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),Contract.View {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var presenter: Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        presenter = Presenter(this,Model())
        binding.bNextCourse.setOnClickListener { presenter?.onButtonClick() }
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvCourse.isVisible = false
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
        binding.tvCourse.isVisible = true
    }

    override fun setString(string: String?) {
        binding.tvCourse.text = string
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}