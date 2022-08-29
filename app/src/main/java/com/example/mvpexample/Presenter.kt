package com.example.mvpexample

class Presenter(
    private var mainView: Contract.View?,
    private var model: Contract.Model,
) : Contract.Presenter, Contract.Model.OnFinishedListener {

    override fun onButtonClick() {
        if (mainView != null) {
            mainView!!.showProgress()
        }
        model.getNextCourse(this)
    }


    override fun onFinished(string: String?) {
        if (mainView != null) {
            mainView!!.setString(string)
            mainView!!.hideProgress()
        }
    }

    override fun onDestroy() {
        mainView = null
    }
}