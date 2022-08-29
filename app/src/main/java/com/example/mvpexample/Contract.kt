package com.example.mvpexample

interface Contract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun setString(string: String?)
    }
    interface Model {
        fun getNextCourse(onFinishedListener: OnFinishedListener?)

        interface OnFinishedListener{
            fun onFinished(string: String?)
        }
    }
    interface Presenter {
        fun onButtonClick()
        fun onDestroy()
    }
}