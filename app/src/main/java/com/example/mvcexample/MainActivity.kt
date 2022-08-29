package com.example.mvcexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.mvcexample.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), Observer, View.OnClickListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var myModel: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        myModel = Model()
        myModel.addObserver(this)

        binding.apply {
            button1.setOnClickListener (this@MainActivity)
            button2.setOnClickListener (this@MainActivity)
            button3.setOnClickListener (this@MainActivity)
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        binding.apply {
            button1.text = "Count: ${myModel.getValueAtIndex(0)}"
            button2.text = "Count: ${myModel.getValueAtIndex(1)}"
            button3.text = "Count: ${myModel.getValueAtIndex(2)}"
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button1 ->  myModel.setValueAtIndex(0)
            R.id.button2 -> myModel.setValueAtIndex(1)
            R.id.button3 ->  myModel.setValueAtIndex(2)
        }
    }
}