package com.example.mvpexample

import android.os.Handler
import android.os.Looper
import java.util.*


class Model: Contract.Model {

    private val arrayList = listOf("Android","Flutter","IOS","Frontend")

    override fun getNextCourse(onFinishedListener: Contract.Model.OnFinishedListener?) {
        Handler(Looper.getMainLooper()).postDelayed({
            onFinishedListener!!.onFinished(
                getRandomString
            )
        },1000)
    }

    private val getRandomString: String
        get()  {
            val random = Random()
            val index = random.nextInt(arrayList.size)
            return arrayList[index]
        }
}